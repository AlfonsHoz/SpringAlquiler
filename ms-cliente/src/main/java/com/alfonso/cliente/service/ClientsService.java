package com.alfonso.cliente.service;

import com.alfonso.cliente.dto.ClientRequest;
import com.alfonso.cliente.dto.ClientResponse;

import java.util.List;

public interface ClientsService {
    List<ClientResponse> getAll();

    ClientResponse getByDocumentNumber(String documentNumber);

    ClientResponse getByEmail(String email);

    ClientResponse create(ClientRequest request);

    ClientResponse getById(Long id);

    ClientResponse update(Long id, ClientRequest request);

    void delete(Long id);
}
