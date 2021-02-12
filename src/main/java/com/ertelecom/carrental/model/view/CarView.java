package com.ertelecom.carrental.model.view;

import com.ertelecom.carrental.model.entity.Car;

public class CarView {
    public Long id;
    public String carModel;
    public String plateNumber;
    public String rentalPoint;

    public CarView() {}

    public CarView(Car car){
        this.id = car.getId();
        this.carModel = car.getCarModel().getName();
        this.plateNumber = car.getPlateNumber();
        this.rentalPoint = car.getRentalPoint().getName();
    }
}
