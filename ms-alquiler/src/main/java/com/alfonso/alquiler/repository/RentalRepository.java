package com.alfonso.alquiler.repository;

import com.alfonso.alquiler.models.Rental;
import com.alfonso.alquiler.models.RentalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findByClientId(Long clientId);

    List<Rental> findByStatus(RentalStatus status);

    List<Rental> findByVehicleIdAndStatus(Long vehicleId, RentalStatus status);

    List<Rental> findByVehicleIdInAndStatus(List<Long> vehicleIds, RentalStatus status);

    @Modifying
    @Query("UPDATE Rental r SET r.status = :status WHERE r.id = :id")
    int updateStatus(@Param("id") Long id, @Param("status") RentalStatus status);

    @Query("SELECT r FROM Rental r WHERE r.status IN :statuses AND r.startDate <= :endDate AND r.endDate >= :startDate")
    List<Rental> findOverlappingRentals(@Param("statuses") List<RentalStatus> statuses,
                                        @Param("startDate") LocalDateTime startDate,
                                        @Param("endDate") LocalDateTime endDate);
}
