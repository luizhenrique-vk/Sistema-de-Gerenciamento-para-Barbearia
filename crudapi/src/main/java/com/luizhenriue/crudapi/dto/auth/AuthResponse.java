package com.luizhenriue.crudapi.dto.auth;

import com.luizhenriue.crudapi.model.Role;

public record AuthResponse(
    String token,
    Long userId,
    String name,
    String email,
    Role role
) {
}
