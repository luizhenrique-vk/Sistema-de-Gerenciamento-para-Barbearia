package com.luizhenriue.crudapi.dto.dashboard;

import java.math.BigDecimal;

public record DashboardResponse(
    long totalClients,
    long totalServices,
    long totalAppointments,
    long scheduledAppointments,
    long completedAppointments,
    BigDecimal completedRevenue
) {
}
