package com.ertelecom.carrental.repository;

import com.ertelecom.carrental.model.entity.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarModelRepository extends JpaRepository<CarModel, Long> {
    Optional<CarModel> findByName(String name);
    List<CarModel> findByCarBrandId(long carBrandId);
    List<CarModel> findAll();
}
