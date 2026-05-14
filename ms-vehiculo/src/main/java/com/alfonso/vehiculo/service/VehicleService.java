package com.alfonso.vehiculo.service;

import com.alfonso.vehiculo.dto.VehicleRequest;
import com.alfonso.vehiculo.dto.VehicleResponse;

import java.util.List;

public interface VehicleService {

    List<VehicleResponse> getAll();

    VehicleResponse create(VehicleRequest request);

    VehicleResponse getById(Long id);

    VehicleResponse update(Long id, VehicleRequest request);

    List<VehicleResponse> getAvailable();

    void delete(Long id);
}
