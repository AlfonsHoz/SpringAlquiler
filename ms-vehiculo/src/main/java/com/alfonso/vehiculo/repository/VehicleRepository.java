package com.alfonso.vehiculo.repository;

import com.alfonso.vehiculo.models.Vehicle;
import com.alfonso.vehiculo.models.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository  extends JpaRepository<Vehicle, Long> {

    Vehicle findByPlate(String plate);

    List<Vehicle> findByAvailable(boolean isAvailable);

    List<Vehicle> findByType(VehicleType type);
}
