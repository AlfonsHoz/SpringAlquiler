package com.alfonso.alquiler.clients;

import com.alfonso.alquiler.dto.VehicleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@FeignClient(name = "ms-vehiculo")
public interface VehiclesClient {
    @GetMapping("/api/vehicles/{id}")
    VehicleDto getById(@PathVariable("id") Long id);

    @GetMapping("/api/vehicles")
    List<VehicleDto> getAll();

    @GetMapping("/api/vehicles/available")
    List<VehicleDto> getAvailable();

    @PutMapping("/api/vehicles/{id}")
    VehicleDto updateAvailability(@PathVariable("id") Long id, VehicleDto request);
}
