package com.ertelecom.carrental.service.impl;

import com.ertelecom.carrental.model.entity.Car;
import com.ertelecom.carrental.model.entity.RentalHistory;
import com.ertelecom.carrental.repository.CarRepository;
import com.ertelecom.carrental.repository.RentalHistoryRepository;
import com.ertelecom.carrental.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service("ICar")
public class CarServiceImpl implements ICarService {
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private RentalHistoryRepository rentalHistoryRepository;

    @Override
    public List<Car> load(){
        List<Car> list = carRepository.findAll();
        return list;
    }

    @Override
    public Optional<Car> loadById(long id){
        return carRepository.findById(id);
    }

    @Override
    public Car save(Car car) throws SQLException {
        if (car.getId() != null){
            return update(car);
        } else {
            return insert(car);
        }
    }

    private Car update(Car car) throws SQLException {
        Optional<Car> carOld = carRepository.findById(car.getId());
        if (!carOld.isPresent()){
            throw new SQLException("Машина не найдена");
        }

        Optional<RentalHistory> rentalHistory =
                rentalHistoryRepository.findByCarIdAndDateEnd(car.getId(), null);
        if (rentalHistory.isPresent() && car.getRentalPointId() != null && car.getRentalPointId() > 0){
            throw new SQLException("Данная машина находится в аредне. Установить точку проката невозможно");
        }

        carRepository.save(car);
        return car;
    }

    private Car insert(Car car){
        carRepository.save(car);
        return car;
    }

    @Override
    public boolean deleteById(long id){
        if (carRepository.findById(id).isPresent()) {
            carRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
