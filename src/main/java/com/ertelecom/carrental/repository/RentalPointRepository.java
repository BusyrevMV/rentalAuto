package com.ertelecom.carrental.repository;

import com.ertelecom.carrental.model.entity.RentalPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentalPointRepository extends JpaRepository<RentalPoint, Long> {
    Optional<RentalPoint> findByName(String name);
    List<RentalPoint> findAll();
}
