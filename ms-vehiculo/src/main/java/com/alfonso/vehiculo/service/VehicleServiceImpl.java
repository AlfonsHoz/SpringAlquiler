package com.alfonso.vehiculo.service;

import com.alfonso.vehiculo.dto.VehicleRequest;
import com.alfonso.vehiculo.dto.VehicleResponse;
import com.alfonso.vehiculo.exceptions.ResourceNotFoundException;
import com.alfonso.vehiculo.models.Vehicle;
import com.alfonso.vehiculo.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository repository;

    public VehicleServiceImpl(VehicleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<VehicleResponse> getAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public VehicleResponse create(VehicleRequest request) {
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(request.getBrand());
        vehicle.setModel(request.getModel());
        vehicle.setAvailable(true);
        vehicle.setYear(request.getYear());
        vehicle.setPlate(request.getPlate());
        vehicle.setColor(request.getColor());
        vehicle.setPricePerDay(request.getPricePerDay());
        vehicle.setType(request.getType());
        repository.save(vehicle);
        return toResponse(vehicle);
    }

    @Override
    public VehicleResponse getById(Long id) {
        Vehicle response = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
        return toResponse(response);
    }

    @Override
    public VehicleResponse update(Long id, VehicleRequest request) {
        Vehicle found = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
        found.setBrand(request.getBrand());
        found.setModel(request.getModel());
        found.setYear(request.getYear());
        found.setPlate(request.getPlate());
        found.setColor(request.getColor());
        found.setPricePerDay(request.getPricePerDay());
        found.setType(request.getType());
        return toResponse(repository.save(found));
    }

    @Override
    public List<VehicleResponse> getAvailable() {
        List<Vehicle> found = repository.findByAvailable(true);
        return found.stream().map(this::toResponse).toList();
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicle not found");
        }
        repository.deleteById(id);
    }

    private VehicleResponse toResponse(Vehicle vehicle) {
        VehicleResponse response = new VehicleResponse();
        response.setId(vehicle.getId());
        response.setBrand(vehicle.getBrand());
        response.setModel(vehicle.getModel());
        response.setAvailable(vehicle.getAvailable());
        response.setYear(vehicle.getYear());
        response.setPlate(vehicle.getPlate());
        response.setColor(vehicle.getColor());
        response.setPricePerDay(vehicle.getPricePerDay());
        response.setType(vehicle.getType());
        return response;
    }
}
