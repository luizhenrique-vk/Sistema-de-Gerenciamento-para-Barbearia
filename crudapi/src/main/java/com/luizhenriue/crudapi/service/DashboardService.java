package com.luizhenriue.crudapi.service;

import com.luizhenriue.crudapi.dto.dashboard.DashboardResponse;
import com.luizhenriue.crudapi.model.AppointmentStatus;
import com.luizhenriue.crudapi.repository.AppointmentRepository;
import com.luizhenriue.crudapi.repository.ClientRepository;
import com.luizhenriue.crudapi.repository.ServiceOfferingRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ClientRepository clientRepository;
    private final ServiceOfferingRepository serviceOfferingRepository;
    private final AppointmentRepository appointmentRepository;

    public DashboardResponse getSummary() {
        var appointments = appointmentRepository.findAll();
        long scheduledAppointments = appointments.stream().filter(a -> a.getStatus() == AppointmentStatus.SCHEDULED).count();
        long completedAppointments = appointments.stream().filter(a -> a.getStatus() == AppointmentStatus.COMPLETED).count();
        BigDecimal revenue = appointments.stream()
            .filter(a -> a.getStatus() == AppointmentStatus.COMPLETED)
            .map(a -> a.getService().getPrice())
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new DashboardResponse(
            clientRepository.count(),
            serviceOfferingRepository.count(),
            appointments.size(),
            scheduledAppointments,
            completedAppointments,
            revenue
        );
    }
}
