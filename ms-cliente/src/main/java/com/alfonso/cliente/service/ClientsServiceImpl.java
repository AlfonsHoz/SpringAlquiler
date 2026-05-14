package com.alfonso.cliente.service;

import com.alfonso.cliente.dto.ClientRequest;
import com.alfonso.cliente.dto.ClientResponse;
import com.alfonso.cliente.exceptions.ClientNotFoundException;
import com.alfonso.cliente.models.Client;
import com.alfonso.cliente.repository.ClientsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientsServiceImpl implements ClientsService {
    private final ClientsRepository repository;

    public ClientsServiceImpl(ClientsRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ClientResponse> getAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public ClientResponse getByDocumentNumber(String documentNumber) {
        Client client = repository.findByDocumentNumber(documentNumber);
        if (client == null) {
            throw new ClientNotFoundException("Client not found");
        }
        return toResponse(client);
    }

    @Override
    public ClientResponse getByEmail(String email) {
        Client client = repository.findByEmail(email);
        if (client == null) {
            throw new ClientNotFoundException("Client not found");
        }
        return toResponse(client);
    }

    @Override
    public ClientResponse create(ClientRequest request) {
        Client client = new Client();
        client.setName(request.getName());
        client.setLastName(request.getLastName());
        client.setEmail(request.getEmail());
        client.setPhone(request.getPhone());
        client.setDocumentNumber(request.getDocumentNumber());
        client.setAddress(request.getAddress());
        repository.save(client);
        return toResponse(client);
    }

    @Override
    public ClientResponse getById(Long id) {
        return toResponse(repository.findById(id).orElseThrow(() -> new ClientNotFoundException("Client not found")));
    }

    @Override
    public ClientResponse update(Long id, ClientRequest request) {
        Client found = repository.findById(id).orElseThrow(() -> new ClientNotFoundException("Client not found"));
        found.setName(request.getName());
        found.setLastName(request.getLastName());
        found.setEmail(request.getEmail());
        found.setPhone(request.getPhone());
        found.setDocumentNumber(request.getDocumentNumber());
        found.setAddress(request.getAddress());
        repository.save(found);
        return toResponse(found);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ClientNotFoundException("Client not found");
        }
        repository.deleteById(id);
    }

    private ClientResponse toResponse(Client client) {
        ClientResponse response = new ClientResponse();
        response.setId(client.getId());
        response.setName(client.getName());
        response.setLastName(client.getLastName());
        response.setEmail(client.getEmail());
        response.setPhone(client.getPhone());
        response.setDocumentNumber(client.getDocumentNumber());
        response.setAddress(client.getAddress());
        return response;
    }
}
