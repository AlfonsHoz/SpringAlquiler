package com.alfonso.alquiler.service;

import com.alfonso.alquiler.dto.RentalRequest;
import com.alfonso.alquiler.dto.RentalResponse;
import com.alfonso.alquiler.dto.VehicleDto;
import com.alfonso.alquiler.models.RentalStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface RentalService {

    RentalResponse getById(Long id);

    List<RentalResponse> getAll();

    RentalResponse create(RentalRequest request);

    RentalResponse complete(Long id);

    RentalResponse cancel(Long id);

    void delete(Long id);

    List<VehicleDto> getAvailableVehicles(LocalDateTime startDate, LocalDateTime endDate, String type);

    List<RentalResponse> getByClientId(Long clientId);

    List<RentalResponse> getByStatus(RentalStatus status);

    List<RentalResponse> getByVehicleIdAndStatus(Long vehicleId, RentalStatus status);

    List<RentalResponse> getByVehicleIdInAndStatus(List<Long> vehicleIds, RentalStatus status);
}
