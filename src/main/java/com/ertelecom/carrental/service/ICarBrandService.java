package com.ertelecom.carrental.service;

import com.ertelecom.carrental.model.entity.CarBrand;

import java.util.List;
import java.util.Optional;

public interface ICarBrandService {
    List<CarBrand> load();

    Optional<CarBrand> loadById(long id);

    CarBrand save(CarBrand carBrand);

    boolean deleteById(long id);
}
