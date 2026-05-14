package com.alfonso.cliente.controller;

import com.alfonso.cliente.dto.ClientRequest;
import com.alfonso.cliente.dto.ClientResponse;
import com.alfonso.cliente.service.ClientsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/clients")
public class ClientsController {
    private final ClientsService clientsService;

    public ClientsController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @GetMapping
    public List<ClientResponse> getAll() {
        return clientsService.getAll();
    }

    @GetMapping("/{id}")
    public ClientResponse getById(@PathVariable Long id) {
        return clientsService.getById(id);
    }

    @GetMapping("/email")
    public ClientResponse getByEmail(@RequestParam String email) {
        return clientsService.getByEmail(email);
    }

    @GetMapping("/document/{documentNumber}")
    public ClientResponse getByDocumentNumber(@PathVariable String documentNumber) {
        return clientsService.getByDocumentNumber(documentNumber);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientResponse create(@RequestBody ClientRequest request) {
        return clientsService.create(request);
    }

    @PutMapping("/{id}")
    public ClientResponse update(@PathVariable Long id, @RequestBody ClientRequest request) {
        return clientsService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        clientsService.delete(id);
    }

}
