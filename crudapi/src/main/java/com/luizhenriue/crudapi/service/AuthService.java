package com.luizhenriue.crudapi.service;

import com.luizhenriue.crudapi.dto.auth.AuthResponse;
import com.luizhenriue.crudapi.dto.auth.LoginRequest;
import com.luizhenriue.crudapi.dto.auth.RegisterRequest;
import com.luizhenriue.crudapi.dto.auth.UserResponse;
import com.luizhenriue.crudapi.entity.User;
import com.luizhenriue.crudapi.exception.BusinessException;
import com.luizhenriue.crudapi.exception.ResourceNotFoundException;
import com.luizhenriue.crudapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessException("Ja existe um usuario com este e-mail");
        }
        if (request.role().name().equals("ADMIN")) {
            throw new BusinessException("O cadastro publico nao permite criar usuarios administradores");
        }

        User user = userRepository.save(User.builder()
            .name(request.name())
            .email(request.email())
            .password(passwordEncoder.encode(request.password()))
            .role(request.role())
            .build());

        return buildAuthResponse(user);
    }

    public AuthResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        User user = userRepository.findByEmail(request.email())
            .orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado"));

        return buildAuthResponse(user);
    }

    public UserResponse getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado"));

        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }

    private AuthResponse buildAuthResponse(User user) {
        String token = jwtService.generateToken(userDetailsService.loadUserByUsername(user.getEmail()));
        return new AuthResponse(token, user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
}
