package com.luizhenriue.crudapi.dto.appointment;

import com.luizhenriue.crudapi.model.AppointmentStatus;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record AppointmentRequest(
    @NotNull(message = "O cliente e obrigatorio")
    Long clientId,
    @NotNull(message = "O servico e obrigatorio")
    Long serviceId,
    @NotNull(message = "O barbeiro e obrigatorio")
    Long barberId,
    @NotNull(message = "A data e obrigatoria")
    LocalDateTime appointmentDateTime,
    @NotNull(message = "O status e obrigatorio")
    AppointmentStatus status,
    String notes
) {
}
