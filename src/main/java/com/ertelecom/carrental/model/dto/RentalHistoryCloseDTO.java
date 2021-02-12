package com.ertelecom.carrental.model.dto;

import com.ertelecom.carrental.model.entity.RentalHistory;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

public class RentalHistoryCloseDTO {

    @NotNull(message = "Поле \"Идентификатор\" не может быть пустым")
    private Long id;

    @NotNull(message = "Поле \"Дата завершения\" не может быть пустым")
    private Timestamp dateEnd;

    private Long progUserIdEnd;

    @NotNull(message = "Поле \"Пункт проката завершения аренды\" не может быть пустым")
    private Long rentalPointIdEnd;

    @Version
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Timestamp dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Long getProgUserIdEnd() {
        return progUserIdEnd;
    }

    public void setProgUserIdEnd(Long progUserIdEnd) {
        this.progUserIdEnd = progUserIdEnd;
    }

    public Long getRentalPointIdEnd() {
        return rentalPointIdEnd;
    }

    public void setRentalPointIdEnd(Long rentalPointIdEnd) {
        this.rentalPointIdEnd = rentalPointIdEnd;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public RentalHistory fillToEntity(RentalHistory rentalHistory){
        rentalHistory.setId(id);
        rentalHistory.setDateEnd(dateEnd);
        rentalHistory.setProgUserIdEnd(progUserIdEnd);
        rentalHistory.setRentalPointIdEnd(rentalPointIdEnd);
        rentalHistory.setVersion(version);

        return rentalHistory;
    }
}
