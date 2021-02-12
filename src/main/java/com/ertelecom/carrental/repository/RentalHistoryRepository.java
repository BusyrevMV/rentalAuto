package com.ertelecom.carrental.repository;

import com.ertelecom.carrental.model.entity.RentalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface RentalHistoryRepository extends JpaRepository<RentalHistory, Long> {
    List<RentalHistory> findByClientId(long clientId);
    List<RentalHistory> findByCarId(long carId);
    Optional<RentalHistory> findByCarIdAndDateEnd(long carId, Timestamp dateEnd);
    List<RentalHistory> findAll();
}
