package com.alfonso.vehiculo.controller;

import com.alfonso.vehiculo.dto.VehicleRequest;
import com.alfonso.vehiculo.dto.VehicleResponse;
import com.alfonso.vehiculo.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService service) {
        this.vehicleService = service;
    }

    @GetMapping(path = "/{id}")
    public VehicleResponse getById(@PathVariable Long id) {
        return vehicleService.getById(id);
    }

    @GetMapping()
    public List<VehicleResponse> getAll() {
        return vehicleService.getAll();
    }

    @GetMapping(path = "/available")
    public List<VehicleResponse> getAvailable() {
        return vehicleService.getAvailable();
    }

    @PostMapping
    public VehicleResponse create(@RequestBody VehicleRequest request) {
        return vehicleService.create(request);
    }

    @PutMapping(path = "/{id}")
    public VehicleResponse update(@PathVariable Long id, @RequestBody VehicleRequest request) {
        return vehicleService.update(id, request);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        vehicleService.delete(id);
    }
}
