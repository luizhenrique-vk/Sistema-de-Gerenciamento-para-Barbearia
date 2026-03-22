package com.luizhenriue.crudapi.repository;

import com.luizhenriue.crudapi.entity.ServiceOffering;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceOfferingRepository extends JpaRepository<ServiceOffering, Long> {

    List<ServiceOffering> findByActiveTrue();
}
