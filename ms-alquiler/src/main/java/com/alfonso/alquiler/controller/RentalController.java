package com.alfonso.alquiler.controller;

import com.alfonso.alquiler.dto.RentalRequest;
import com.alfonso.alquiler.dto.RentalResponse;
import com.alfonso.alquiler.dto.VehicleDto;
import com.alfonso.alquiler.models.RentalStatus;
import com.alfonso.alquiler.service.RentalService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/api/rentals")
public class RentalController {

    @GetMapping(path = "/available")
    public List<VehicleDto> getAvailableVehicles(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(value = "type", required = false) String type) {
        return rentalService.getAvailableVehicles(startDate, endDate, type);
    }

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("/{id}")
    public RentalResponse getById(@PathVariable Long id) {
        return rentalService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RentalResponse create(@RequestBody RentalRequest request) {
        return rentalService.create(request);
    }

    @PutMapping("/{id}/complete")
    public RentalResponse complete(@PathVariable Long id) {
        return rentalService.complete(id);
    }

    @PutMapping("/{id}/cancel")
    public RentalResponse cancel(@PathVariable Long id) {
        return rentalService.cancel(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        rentalService.delete(id);
    }

    @GetMapping("/client/{clientId}")
    public List<RentalResponse> getByClientId(@PathVariable Long clientId) {
        return rentalService.getByClientId(clientId);
    }

    @GetMapping("/status/{status}")
    public List<RentalResponse> getByStatus(@PathVariable RentalStatus status) {
        return rentalService.getByStatus(status);
    }

    @GetMapping
    public List<RentalResponse> getAll() {
        return rentalService.getAll();
    }
}
