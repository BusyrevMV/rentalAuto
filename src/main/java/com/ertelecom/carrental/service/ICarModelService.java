package com.ertelecom.carrental.service;

import com.ertelecom.carrental.model.entity.CarModel;

import java.util.List;
import java.util.Optional;

public interface ICarModelService {
    List<CarModel> load();

    Optional<CarModel> loadById(long id);

    CarModel save(CarModel carModel);

    boolean deleteById(long id);
}
