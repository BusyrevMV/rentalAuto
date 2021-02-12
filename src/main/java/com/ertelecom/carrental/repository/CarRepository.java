package com.ertelecom.carrental.repository;

import com.ertelecom.carrental.model.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findByPlateNumber(String plateNumber);
    List<Car> findByRentalPointId(long rentalPointId);
    List<Car> findByCarModelId(long carModelId);
    List<Car> findAll();
}
