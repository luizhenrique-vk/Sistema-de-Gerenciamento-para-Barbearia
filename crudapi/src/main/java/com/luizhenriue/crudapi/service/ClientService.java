package com.luizhenriue.crudapi.service;

import com.luizhenriue.crudapi.dto.client.ClientRequest;
import com.luizhenriue.crudapi.entity.Client;
import com.luizhenriue.crudapi.exception.BusinessException;
import com.luizhenriue.crudapi.exception.ResourceNotFoundException;
import com.luizhenriue.crudapi.repository.ClientRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public List<Client> findAll(String name) {
        if (name == null || name.isBlank()) {
            return clientRepository.findAll();
        }
        return clientRepository.findByNameContainingIgnoreCase(name);
    }

    public Client findById(Long id) {
        return clientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente nao encontrado"));
    }

    public Client create(ClientRequest request) {
        validateUniqueFields(request, null);
        Client client = Client.builder()
            .name(request.name())
            .phone(request.phone())
            .email(request.email())
            .notes(request.notes())
            .build();
        return clientRepository.save(client);
    }

    public Client update(Long id, ClientRequest request) {
        Client client = findById(id);
        validateUniqueFields(request, id);
        client.setName(request.name());
        client.setPhone(request.phone());
        client.setEmail(request.email());
        client.setNotes(request.notes());
        return clientRepository.save(client);
    }

    public void delete(Long id) {
        clientRepository.delete(findById(id));
    }

    private void validateUniqueFields(ClientRequest request, Long currentId) {
        clientRepository.findAll().forEach(client -> {
            if (!client.getId().equals(currentId)) {
                if (client.getEmail().equalsIgnoreCase(request.email())) {
                    throw new BusinessException("Ja existe um cliente com este e-mail");
                }
                if (client.getPhone().equalsIgnoreCase(request.phone())) {
                    throw new BusinessException("Ja existe um cliente com este telefone");
                }
            }
        });
    }
}
