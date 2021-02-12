package com.ertelecom.carrental.model.view;

import com.ertelecom.carrental.model.entity.CarBrand;

public class CarBrandView {
    public Long id;
    public String name;

    public CarBrandView() {}

    public CarBrandView(CarBrand carBrand){
        this.id = carBrand.getId();
        this.name = carBrand.getName();
    }
}
