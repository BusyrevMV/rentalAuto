package com.ertelecom.carrental.model.entity;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@OptimisticLocking(type = OptimisticLockType.VERSION)
@Table(name = "rentalpoint")
public class RentalPoint {

    @Id
    @SequenceGenerator(name = "rentalpointIdSeq", sequenceName = "rentalpoint_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rentalpointIdSeq")
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "Поле \"Название\" не может быть пустым")
    @Length(max = 255, message = "Поле \"Название\" не может быть больше 255 символов")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Поле \"Адрес\" не может быть пустым")
    @Length(max = 255, message = "Поле \"Адрес\" не может быть больше 255 символов")
    @Column(name = "address", nullable = false)
    private String address;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
