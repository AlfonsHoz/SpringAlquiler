package com.alfonso.alquiler.service;

import com.alfonso.alquiler.clients.ClientsClient;
import com.alfonso.alquiler.clients.VehiclesClient;
import com.alfonso.alquiler.dto.RentalRequest;
import com.alfonso.alquiler.dto.RentalResponse;
import com.alfonso.alquiler.dto.VehicleDto;
import com.alfonso.alquiler.exceptions.ResourceNotFoundException;
import com.alfonso.alquiler.models.Rental;
import com.alfonso.alquiler.models.RentalStatus;
import com.alfonso.alquiler.repository.RentalRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class RentalServiceImpl implements RentalService {
    private final RentalRepository repository;
    private final ClientsClient clients;
    private final VehiclesClient vehicles;

    RentalServiceImpl(RentalRepository rentalRepository, ClientsClient clientsClient, VehiclesClient vehiclesClient) {
        this.repository = rentalRepository;
        this.clients = clientsClient;
        this.vehicles = vehiclesClient;
    }

    @Override
    public RentalResponse getById(Long id) {
        return repository.findById(id).map(this::toResponse).orElseThrow(() -> new ResourceNotFoundException("Rental not found"));
    }

    @Override
    public List<RentalResponse> getAll() {
        List<Rental> rentals = repository.findAll();
        return rentals.stream().map(this::toResponse).toList();
    }

    @Override
    public RentalResponse create(RentalRequest request) {
        try {
            if (validateAvailability(request)) {
                Rental rental = new Rental();
                rental.setClientId(request.getClientId());
                rental.setVehicleId(request.getVehicleId());
                rental.setStartDate(LocalDateTime.parse(request.getStartDate()));
                rental.setEndDate(LocalDateTime.parse(request.getEndDate()));
                rental.setTotalAmount(calculateRentalAmount(rental, vehicles.getById(request.getVehicleId())));
                rental.setStatus(RentalStatus.PENDING);
                repository.save(rental);
                return toResponse(rental);
            } else {
                throw new ResourceNotFoundException("Cannot create Rental");
            }
        } catch (Exception e) {
            throw new ResourceNotFoundException("Vehicle's MS it's down");
        }
    }

    @Override
    @Transactional
    public RentalResponse complete(Long id) {
        try {
            repository.updateStatus(id, RentalStatus.COMPLETED);
            Rental rental = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rental not found"));
            VehicleDto vehicle = vehicles.getById(rental.getVehicleId());
            vehicle.setAvailable(true);
            vehicles.updateAvailability(rental.getVehicleId(), vehicle);
            return toResponse(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rental not found")));
        } catch (Exception e) {
            throw new ResourceNotFoundException("Vehicle's MS it's down");
        }
    }

    @Override
    @Transactional
    public RentalResponse cancel(Long id) {
        try {
            repository.updateStatus(id, RentalStatus.CANCELLED);
            Rental rental = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rental not found"));
            VehicleDto vehicle = vehicles.getById(rental.getVehicleId());
            vehicle.setAvailable(true);
            vehicles.updateAvailability(rental.getVehicleId(), vehicle);
            return toResponse(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rental not found")));
        } catch (Exception e) {
            throw new ResourceNotFoundException("Vehicle's MS it's down");
        }
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete Rental");
        }
        repository.deleteById(id);
    }

    @Override
    public List<VehicleDto> getAvailableVehicles(LocalDateTime startDate, LocalDateTime endDate, String type) {
        try {
            List<Rental> rentals = repository.findOverlappingRentals(List.of(RentalStatus.PENDING, RentalStatus.ACTIVE), startDate, endDate);
            List<Long> vehicleIds = rentals.stream().map(Rental::getVehicleId).toList();
            List<VehicleDto> availableVehicles = vehicles.getAvailable();
            if (type != null) {
                availableVehicles = availableVehicles.stream().filter(
                        vehicle -> vehicle.getType().equals(type)
                ).toList();
            }
            return availableVehicles.stream().filter(
                    vehicle -> !vehicleIds.contains(vehicle.getId())
            ).toList();
        } catch (Exception e) {
            throw new ResourceNotFoundException("Vehicle's MS it's down");
        }
    }

    @Override
    public List<RentalResponse> getByClientId(Long clientId) {
        List<Rental> rentals = repository.findByClientId(clientId);
        return rentals.stream().map(this::toResponse).toList();
    }

    @Override
    public List<RentalResponse> getByStatus(RentalStatus status) {
        List<Rental> rentals = repository.findByStatus(status);
        return rentals.stream().map(this::toResponse).toList();
    }

    @Override
    public List<RentalResponse> getByVehicleIdAndStatus(Long vehicleId, RentalStatus status) {
        List<Rental> rentals = repository.findByVehicleIdAndStatus(vehicleId, status);
        return rentals.stream().map(this::toResponse).toList();
    }

    @Override
    public List<RentalResponse> getByVehicleIdInAndStatus(List<Long> vehicleIds, RentalStatus status) {
        List<Rental> rentals = repository.findByVehicleIdInAndStatus(vehicleIds, status);
        return rentals.stream().map(this::toResponse).toList();
    }

    RentalResponse toResponse(Rental rental) {
        RentalResponse response = new RentalResponse();
        VehicleDto vehicle = vehicles.getById(rental.getVehicleId());
        response.setId(rental.getId());
        response.setClientId(rental.getClientId());
        response.setVehicleId(rental.getVehicleId());
        response.setStartDate(rental.getStartDate().toString());
        response.setEndDate(rental.getEndDate().toString());
        response.setTotalAmount(rental.getTotalAmount());
        response.setStatus(rental.getStatus().toString());
        response.setCreatedAt(rental.getCreatedAt().toString());
        response.setUpdatedAt(rental.getUpdatedAt().toString());
        response.setPricePerDay(BigDecimal.valueOf(vehicle.getPricePerDay()));
        return response;
    }

    private boolean validateAvailability(RentalRequest request) {
        try {
            boolean clientExists = clients.getById(request.getClientId()).getId() != null;
            VehicleDto vehicle = vehicles.getById(request.getVehicleId());
            boolean vehicleExists = vehicle.getId() != null;
            boolean isVehicleAvailable = vehicle.getAvailable();
            boolean isOverlap = !repository.findOverlappingRentals(List.of(
                    RentalStatus.PENDING,
                    RentalStatus.ACTIVE
            ), LocalDateTime.parse(request.getStartDate()), LocalDateTime.parse(request.getEndDate())).isEmpty();
            if (!clientExists) {
                throw new ResourceNotFoundException("Client not found");
            }
            if (!vehicleExists) {
                throw new ResourceNotFoundException("Vehicle not found");
            }
            if (!isVehicleAvailable) {
                throw new ResourceNotFoundException("Vehicle not available");
            }
            if (isOverlap) {
                throw new ResourceNotFoundException("Vehicle is already rented");
            }
            return true;
        } catch (Exception e) {
            throw new ResourceNotFoundException("Client's MS it's down");
        }
    }

    private BigDecimal calculateRentalAmount(Rental rental, VehicleDto vehicle) {
        BigDecimal pricePerDay = BigDecimal.valueOf(vehicle.getPricePerDay());
        LocalDateTime startDate = rental.getStartDate();
        LocalDateTime endDate = rental.getEndDate();
        long daysRented = ChronoUnit.DAYS.between(startDate, endDate);

        return pricePerDay.multiply(BigDecimal.valueOf(daysRented));
    }
}
