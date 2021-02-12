package com.ertelecom.carrental.service;

import com.ertelecom.carrental.model.entity.RentalPoint;

import java.util.List;
import java.util.Optional;

public interface IRentalPointService {
    List<RentalPoint> load();

    Optional<RentalPoint> loadById(long id);

    RentalPoint save(RentalPoint rentalPoint);

    boolean deleteById(long id);
}
