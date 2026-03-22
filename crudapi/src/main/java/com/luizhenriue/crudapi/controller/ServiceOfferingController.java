package com.luizhenriue.crudapi.controller;

import com.luizhenriue.crudapi.dto.service.ServiceOfferingRequest;
import com.luizhenriue.crudapi.entity.ServiceOffering;
import com.luizhenriue.crudapi.service.ServiceOfferingService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;

    @GetMapping
    public List<ServiceOffering> findAll(@RequestParam(required = false) Boolean activeOnly) {
        return serviceOfferingService.findAll(activeOnly);
    }

    @GetMapping("/{id}")
    public ServiceOffering findById(@PathVariable Long id) {
        return serviceOfferingService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceOffering create(@Valid @RequestBody ServiceOfferingRequest request) {
        return serviceOfferingService.create(request);
    }

    @PutMapping("/{id}")
    public ServiceOffering update(@PathVariable Long id, @Valid @RequestBody ServiceOfferingRequest request) {
        return serviceOfferingService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        serviceOfferingService.delete(id);
    }
}
