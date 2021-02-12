package com.ertelecom.carrental.model.view;

import com.ertelecom.carrental.model.entity.RentalHistory;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class RentalHistoryView {
    private static SimpleDateFormat sdf =  new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public Long id;
    public String dateBeg;
    public String dateEnd;
    public String progUserBegFIO;
    public String progUserBegUsername;
    public String progUserEndFIO;
    public String progUserEndUsername;
    public String clientFIO;
    public String clientDriverNumber;
    public String carBrand;
    public String carModel;
    public String carPlateNumber;
    public String rentalPointBeg;
    public String rentalPointEnd;

    public RentalHistoryView(){}

    public RentalHistoryView(RentalHistory rentalHistory){
        this.id = rentalHistory.getId();
        this.dateBeg = sdf.format(rentalHistory.getDateBeg());
        setDateEnd(rentalHistory.getDateEnd());
        this.progUserBegFIO = rentalHistory.getProgUserBeg().getFullName();
        this.progUserBegUsername = rentalHistory.getProgUserBeg().getUsername();
        this.progUserEndFIO = rentalHistory.getProgUserEnd().getFullName();
        this.progUserEndUsername = rentalHistory.getProgUserEnd().getUsername();
        this.clientFIO = rentalHistory.getClient().getFullName();
        this.clientDriverNumber = rentalHistory.getClient().getDriverNumber();
        this.carBrand = rentalHistory.getCar().getCarModel().getCarBrand().getName();
        this.carModel = rentalHistory.getCar().getCarModel().getName();
        this.carPlateNumber = rentalHistory.getCar().getPlateNumber();
        this.rentalPointBeg = rentalHistory.getRentalPointBeg().getName();
        this.rentalPointEnd = rentalHistory.getRentalPointEnd() != null
                ? rentalHistory.getRentalPointEnd().getName() : "";
    }

    public void setDateBeg(Timestamp dateBeg) {
        this.dateBeg = dateBeg != null ? sdf.format(dateBeg) : "";
    }

    public void setDateEnd(Timestamp dateEnd) {
        this.dateEnd = dateEnd != null ? sdf.format(dateEnd) : "";
    }
}
