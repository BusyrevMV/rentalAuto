package com.ertelecom.carrental.model.entity;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@OptimisticLocking(type = OptimisticLockType.VERSION)
@Table(name = "client")
public class Client {

    @Id
    @SequenceGenerator(name = "clientIdSeq", sequenceName = "client_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clientIdSeq")
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "Поле \"Имя\" не может быть пустым")
    @Length(max = 100, message = "Поле \"Имя\" не может быть больше 100 символов")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Поле \"Фамилия\" не может быть пустым")
    @Length(max = 100, message = "Поле \"Фамилия\" не может быть больше 100 символов")
    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "patronymic")
    @Length(max = 100, message = "Поле \"Отчество\" не может быть больше 100 символов")
    private String patronymic;

    @NotBlank(message = "Поле \"Водительское удостоверение\" не может быть пустым")
    @Column(name = "drivernumber", nullable = false, unique = true)
    private String driverNumber;

    @Version
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDriverNumber() {
        return driverNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDriverNumber(String driverNumber) {
        this.driverNumber = driverNumber;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getFullName(){
        return surname + " " + name + " " + patronymic != null ? patronymic : "";
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
