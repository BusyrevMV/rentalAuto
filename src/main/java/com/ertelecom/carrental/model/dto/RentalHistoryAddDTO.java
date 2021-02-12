package com.ertelecom.carrental.model.dto;

import com.ertelecom.carrental.model.entity.RentalHistory;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

public class RentalHistoryAddDTO {

    private Timestamp dateBeg;

    private Long progUserIdBeg;

    @NotNull(message = "Поле \"Клиент\" не может быть пустым")
    private Long clientId;

    @NotNull(message = "Поле \"Авто\" не может быть пустым")
    private Long carId;

    @NotNull(message = "Поле \"Пункт проката начала аренды\" не может быть пустым")
    private Long rentalPointIdBeg;

    public Timestamp getDateBeg() {
        return dateBeg;
    }

    public void setDateBeg(Timestamp dateBeg) {
        this.dateBeg = dateBeg;
    }

    public Long getProgUserIdBeg() {
        return progUserIdBeg;
    }

    public void setProgUserIdBeg(Long progUserIdBeg) {
        this.progUserIdBeg = progUserIdBeg;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getRentalPointIdBeg() {
        return rentalPointIdBeg;
    }

    public void setRentalPointIdBeg(Long rentalPointIdBeg) {
        this.rentalPointIdBeg = rentalPointIdBeg;
    }

    public RentalHistory fillToEntity(RentalHistory rentalHistory){
        rentalHistory.setDateBeg(dateBeg);
        rentalHistory.setProgUserIdBeg(progUserIdBeg);
        rentalHistory.setClientId(clientId);
        rentalHistory.setCarId(carId);
        rentalHistory.setRentalPointIdBeg(rentalPointIdBeg);

        return rentalHistory;
    }
}
