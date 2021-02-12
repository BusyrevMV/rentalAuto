package com.ertelecom.carrental.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@OptimisticLocking(type = OptimisticLockType.VERSION)
@Table(name = "car")
public class Car {

    @Id
    @SequenceGenerator(name = "carIdSeq", sequenceName = "car_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carIdSeq")
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull(message = "ППоле \"Модель авто\" не может быть пустым")
    @Column(name = "carmodelid", nullable = false)
    private Long carModelId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carmodelid", referencedColumnName = "id", insertable=false, updatable=false)
    private CarModel carModel;

    @NotBlank(message = "Поле \"Гос. номер\" не может быть пустым")
    @Length(max = 10, message = "Поле \"Гос. номер\" не может быть больше 10 символов")
    @Column(name = "platenumber", nullable = false)
    private String plateNumber;

    @Column(name = "rentalpointid")
    private Long rentalPointId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rentalpointid", referencedColumnName = "id", insertable=false, updatable=false)
    private RentalPoint rentalPoint;

    @Version
    private Long version;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCarModelId() {
        return carModelId;
    }

    public void setCarModelId(Long сarModelId) {
        this.carModelId = сarModelId;
    }

    public CarModel getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModel сarModel) {
        this.carModel = сarModel;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Long getRentalPointId() {
        return rentalPointId;
    }

    public void setRentalPointId(Long rentalPointId) {
        this.rentalPointId = rentalPointId;
    }

    public RentalPoint getRentalPoint() {
        return rentalPoint;
    }

    public void setRentalPoint(RentalPoint rentalPoint) {
        this.rentalPoint = rentalPoint;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
