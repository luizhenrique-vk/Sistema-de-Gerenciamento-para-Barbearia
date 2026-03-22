package com.luizhenriue.crudapi.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @Email(message = "Informe um e-mail valido")
    @NotBlank(message = "O e-mail e obrigatorio")
    String email,
    @NotBlank(message = "A senha e obrigatoria")
    String password
) {
}
