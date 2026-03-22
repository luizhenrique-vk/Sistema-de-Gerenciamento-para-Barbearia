package com.luizhenriue.crudapi.dto.auth;

import com.luizhenriue.crudapi.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank(message = "O nome e obrigatorio")
    String name,
    @Email(message = "Informe um e-mail valido")
    @NotBlank(message = "O e-mail e obrigatorio")
    String email,
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    String password,
    @NotNull(message = "O perfil e obrigatorio")
    Role role
) {
}
