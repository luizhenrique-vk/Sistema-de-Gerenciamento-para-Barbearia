package com.luizhenriue.crudapi.dto.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClientRequest(
    @NotBlank(message = "O nome e obrigatorio")
    String name,
    @NotBlank(message = "O telefone e obrigatorio")
    @Size(min = 8, max = 20, message = "Informe um telefone valido")
    String phone,
    @Email(message = "Informe um e-mail valido")
    @NotBlank(message = "O e-mail e obrigatorio")
    String email,
    String notes
) {
}
