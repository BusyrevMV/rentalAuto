package com.ertelecom.carrental.model.view;

import com.ertelecom.carrental.model.entity.CarModel;

public class CarModelView {
    public Long id;
    public String name;
    public String carBrand;

    public CarModelView() {}

    public CarModelView(CarModel carModel){
        this.id = carModel.getId();
        this.name = carModel.getName();
        this.carBrand = carModel.getCarBrand().getName();
    }
}
