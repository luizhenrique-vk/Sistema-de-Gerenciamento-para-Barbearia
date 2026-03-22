package com.luizhenriue.crudapi.repository;

import com.luizhenriue.crudapi.entity.User;
import com.luizhenriue.crudapi.model.Role;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByRoleIn(List<Role> roles);
}
