package com.alfonso.cliente.repository;

import com.alfonso.cliente.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientsRepository extends JpaRepository<Client, Long> {
    Client findByEmail(String email);

    Client findByDocumentNumber(String documentNumber);
}
