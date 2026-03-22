package com.luizhenriue.crudapi.repository;

import com.luizhenriue.crudapi.entity.Client;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    List<Client> findByNameContainingIgnoreCase(String name);
}
