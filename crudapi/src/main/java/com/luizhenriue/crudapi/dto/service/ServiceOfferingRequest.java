package com.luizhenriue.crudapi.dto.service;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ServiceOfferingRequest(
    @NotBlank(message = "O nome do servico e obrigatorio")
    String name,
    String description,
    @NotNull(message = "O preco e obrigatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preco deve ser maior que zero")
    BigDecimal price,
    @NotNull(message = "A duracao e obrigatoria")
    @Min(value = 5, message = "A duracao minima e de 5 minutos")
    Integer durationMinutes,
    @NotNull(message = "Informe se o servico esta ativo")
    Boolean active
) {
}
