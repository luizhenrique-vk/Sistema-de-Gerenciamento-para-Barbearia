package com.luizhenriue.crudapi.dto.appointment;

import com.luizhenriue.crudapi.model.AppointmentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AppointmentResponse(
    Long id,
    Long clientId,
    String clientName,
    Long serviceId,
    String serviceName,
    BigDecimal servicePrice,
    Integer durationMinutes,
    Long barberId,
    String barberName,
    AppointmentStatus status,
    LocalDateTime appointmentDateTime,
    String notes
) {
}
