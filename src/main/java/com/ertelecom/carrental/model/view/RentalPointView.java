package com.ertelecom.carrental.model.view;

import com.ertelecom.carrental.model.entity.RentalPoint;

public class RentalPointView {
    public Long id;
    public String name;
    public String address;

    public RentalPointView(){};

    public RentalPointView(RentalPoint rentalPoint) {
        this.id = rentalPoint.getId();
        this.name = rentalPoint.getName();
        this.address = rentalPoint.getAddress();
    }
}
