package com.luizhenriue.crudapi.service;

import com.luizhenriue.crudapi.dto.auth.UserResponse;
import com.luizhenriue.crudapi.model.Role;
import com.luizhenriue.crudapi.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> findAll() {
        return userRepository.findAll().stream()
            .map(user -> new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole()))
            .toList();
    }

    public List<UserResponse> findBarbers() {
        return userRepository.findByRoleIn(List.of(Role.ADMIN, Role.BARBER)).stream()
            .map(user -> new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole()))
            .toList();
    }
}
