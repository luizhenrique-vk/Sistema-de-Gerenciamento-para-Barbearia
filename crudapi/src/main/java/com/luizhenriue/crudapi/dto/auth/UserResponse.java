package com.luizhenriue.crudapi.dto.auth;

import com.luizhenriue.crudapi.model.Role;

public record UserResponse(
    Long id,
    String name,
    String email,
    Role role
) {
}
