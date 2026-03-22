package com.luizhenriue.crudapi.service;

import com.luizhenriue.crudapi.dto.appointment.AppointmentRequest;
import com.luizhenriue.crudapi.dto.appointment.AppointmentResponse;
import com.luizhenriue.crudapi.entity.Appointment;
import com.luizhenriue.crudapi.entity.Client;
import com.luizhenriue.crudapi.entity.ServiceOffering;
import com.luizhenriue.crudapi.entity.User;
import com.luizhenriue.crudapi.exception.BusinessException;
import com.luizhenriue.crudapi.exception.ResourceNotFoundException;
import com.luizhenriue.crudapi.model.AppointmentStatus;
import com.luizhenriue.crudapi.model.Role;
import com.luizhenriue.crudapi.repository.AppointmentRepository;
import com.luizhenriue.crudapi.repository.ClientRepository;
import com.luizhenriue.crudapi.repository.ServiceOfferingRepository;
import com.luizhenriue.crudapi.repository.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ClientRepository clientRepository;
    private final ServiceOfferingRepository serviceOfferingRepository;
    private final UserRepository userRepository;

    public List<AppointmentResponse> findAll(LocalDate date, AppointmentStatus status) {
        List<Appointment> appointments;
        if (date != null) {
            appointments = appointmentRepository.findByAppointmentDateTimeBetweenOrderByAppointmentDateTimeAsc(
                date.atStartOfDay(),
                date.plusDays(1).atStartOfDay()
            );
        } else if (status != null) {
            appointments = appointmentRepository.findByStatusOrderByAppointmentDateTimeAsc(status);
        } else {
            appointments = appointmentRepository.findAll();
        }
        return appointments.stream().map(this::toResponse).toList();
    }

    public AppointmentResponse findById(Long id) {
        return toResponse(getAppointment(id));
    }

    public AppointmentResponse create(AppointmentRequest request) {
        Appointment appointment = buildAppointment(request, Appointment.builder().build(), null);
        return toResponse(appointmentRepository.save(appointment));
    }

    public AppointmentResponse update(Long id, AppointmentRequest request) {
        Appointment appointment = getAppointment(id);
        appointment = buildAppointment(request, appointment, id);
        return toResponse(appointmentRepository.save(appointment));
    }

    public void delete(Long id) {
        appointmentRepository.delete(getAppointment(id));
    }

    private Appointment getAppointment(Long id) {
        return appointmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Agendamento nao encontrado"));
    }

    private Appointment buildAppointment(AppointmentRequest request, Appointment appointment, Long appointmentId) {
        Client client = clientRepository.findById(request.clientId())
            .orElseThrow(() -> new ResourceNotFoundException("Cliente nao encontrado"));
        ServiceOffering service = serviceOfferingRepository.findById(request.serviceId())
            .orElseThrow(() -> new ResourceNotFoundException("Servico nao encontrado"));
        User barber = userRepository.findById(request.barberId())
            .orElseThrow(() -> new ResourceNotFoundException("Barbeiro nao encontrado"));

        if (barber.getRole() == Role.RECEPTIONIST) {
            throw new BusinessException("O usuario selecionado nao pode receber agendamentos como barbeiro");
        }

        if (Boolean.FALSE.equals(service.getActive())) {
            throw new BusinessException("Nao e possivel agendar um servico inativo");
        }

        if (request.status() == AppointmentStatus.SCHEDULED && request.appointmentDateTime().isBefore(LocalDateTime.now())) {
            throw new BusinessException("Agendamentos marcados devem estar em uma data futura");
        }

        LocalDateTime endTime = request.appointmentDateTime().plusMinutes(service.getDurationMinutes());
        boolean hasConflict = appointmentRepository.findByBarberIdAndStatusOrderByAppointmentDateTimeAsc(
                barber.getId(),
                AppointmentStatus.SCHEDULED
            ).stream()
            .filter(existing -> appointmentId == null || !existing.getId().equals(appointmentId))
            .anyMatch(existing -> hasTimeConflict(
                request.appointmentDateTime(),
                endTime,
                existing.getAppointmentDateTime(),
                existing.getAppointmentDateTime().plusMinutes(existing.getService().getDurationMinutes())
            ));

        if (request.status() == AppointmentStatus.SCHEDULED && hasConflict) {
            throw new BusinessException("Ja existe um agendamento conflitante para este barbeiro");
        }

        appointment.setClient(client);
        appointment.setService(service);
        appointment.setBarber(barber);
        appointment.setAppointmentDateTime(request.appointmentDateTime());
        appointment.setStatus(request.status());
        appointment.setNotes(request.notes());
        return appointment;
    }

    private boolean hasTimeConflict(
        LocalDateTime requestedStart,
        LocalDateTime requestedEnd,
        LocalDateTime existingStart,
        LocalDateTime existingEnd
    ) {
        return requestedStart.isBefore(existingEnd) && requestedEnd.isAfter(existingStart);
    }

    private AppointmentResponse toResponse(Appointment appointment) {
        return new AppointmentResponse(
            appointment.getId(),
            appointment.getClient().getId(),
            appointment.getClient().getName(),
            appointment.getService().getId(),
            appointment.getService().getName(),
            appointment.getService().getPrice(),
            appointment.getService().getDurationMinutes(),
            appointment.getBarber().getId(),
            appointment.getBarber().getName(),
            appointment.getStatus(),
            appointment.getAppointmentDateTime(),
            appointment.getNotes()
        );
    }
}
