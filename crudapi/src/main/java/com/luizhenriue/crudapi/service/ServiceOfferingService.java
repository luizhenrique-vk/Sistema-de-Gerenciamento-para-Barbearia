package com.luizhenriue.crudapi.service;

import com.luizhenriue.crudapi.dto.service.ServiceOfferingRequest;
import com.luizhenriue.crudapi.entity.ServiceOffering;
import com.luizhenriue.crudapi.exception.ResourceNotFoundException;
import com.luizhenriue.crudapi.repository.ServiceOfferingRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceOfferingService {

    private final ServiceOfferingRepository serviceOfferingRepository;

    public List<ServiceOffering> findAll(Boolean activeOnly) {
        if (Boolean.TRUE.equals(activeOnly)) {
            return serviceOfferingRepository.findByActiveTrue();
        }
        return serviceOfferingRepository.findAll();
    }

    public ServiceOffering findById(Long id) {
        return serviceOfferingRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Servico nao encontrado"));
    }

    public ServiceOffering create(ServiceOfferingRequest request) {
        return serviceOfferingRepository.save(buildEntity(request, ServiceOffering.builder().build()));
    }

    public ServiceOffering update(Long id, ServiceOfferingRequest request) {
        ServiceOffering serviceOffering = findById(id);
        return serviceOfferingRepository.save(buildEntity(request, serviceOffering));
    }

    public void delete(Long id) {
        serviceOfferingRepository.delete(findById(id));
    }

    private ServiceOffering buildEntity(ServiceOfferingRequest request, ServiceOffering serviceOffering) {
        serviceOffering.setName(request.name());
        serviceOffering.setDescription(request.description());
        serviceOffering.setPrice(request.price());
        serviceOffering.setDurationMinutes(request.durationMinutes());
        serviceOffering.setActive(request.active());
        return serviceOffering;
    }
}
