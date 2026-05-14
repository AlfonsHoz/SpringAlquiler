package com.alfonso.alquiler.clients;

import com.alfonso.alquiler.dto.ClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-cliente")
public interface ClientsClient {
    @GetMapping("/api/clients/{id}")
    ClientDto getById(@PathVariable("id") Long id);

    @GetMapping("/api/clients/email")
    ClientDto getByEmail(@RequestParam("email") String email);

    @GetMapping("/api/clients/document/{documentNumber}")
    ClientDto getByDocumentNumber(@PathVariable("documentNumber") String documentNumber);

}
