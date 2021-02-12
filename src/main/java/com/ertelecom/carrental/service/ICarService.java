package com.ertelecom.carrental.service;

import com.ertelecom.carrental.model.entity.Car;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ICarService {
    List<Car> load();

    Optional<Car> loadById(long id);

    Car save(Car car) throws SQLException;

    boolean deleteById(long id);
}
