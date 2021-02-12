package com.ertelecom.carrental.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@OptimisticLocking(type = OptimisticLockType.VERSION)
@Table(name = "carmodel")
public class CarModel {

    @Id
    @SequenceGenerator(name = "carmodelIdSeq", sequenceName = "carmodel_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carmodelIdSeq")
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull(message = "Поле \"Название\" не может быть пустым")
    @Length(max = 255, message = "Поле \"Название\" не может быть больше 255 символов")
    @Column(name = "name", nullable = false)
    private String name;

    @Min(value = 1, message = "Поле \"Марка авто\" не может быть пустым")
    @Column(name = "carbrandid", nullable = false)
    private Long carBrandId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carbrandid", referencedColumnName = "id", insertable=false, updatable=false)
    private CarBrand carBrand;

    @Version
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCarBrandId() {
        return carBrandId;
    }

    public void setCarBrandId(Long carBrandId) {
        this.carBrandId = carBrandId;
    }

    public CarBrand getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(CarBrand carBrand) {
        this.carBrand = carBrand;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
