package com.ertelecom.carrental.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@OptimisticLocking(type = OptimisticLockType.VERSION)
@Table(name = "rentalhistory")
public class RentalHistory {

    @Id
    @SequenceGenerator(name = "rentalhistoryIdSeq", sequenceName = "rentalhistory_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rentalhistoryIdSeq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "datebeg", nullable = false, updatable = false)
    private Timestamp dateBeg;

    @Column(name = "dateend")
    private Timestamp dateEnd;

    @NotNull(message = "Поле \"Пользователь сдавший авто\" не может быть пустым")
    @Column(name = "proguseridbeg", nullable = false, updatable = false)
    private Long progUserIdBeg;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proguseridbeg", referencedColumnName = "id", insertable = false, updatable = false)
    private ProgUser progUserBeg;

    @Column(name = "proguseridend", nullable = false)
    private Long progUserIdEnd;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proguseridend", referencedColumnName = "id", insertable = false, updatable = false)
    private ProgUser progUserEnd;

    @NotNull(message = "Поле \"Клиент\" не может быть пустым")
    @Column(name = "clientid", nullable = false, updatable = false)
    private Long clientId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clientid", referencedColumnName = "id", insertable = false, updatable = false)
    private Client client;

    @NotNull(message = "Поле \"Авто\" не может быть пустым")
    @Column(name = "carid", nullable = false, updatable = false)
    private Long carId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carid", referencedColumnName = "id", insertable = false, updatable = false)
    private Car car;

    @NotNull(message = "Поле \"Пункт проката начала аренды\" не может быть пустым")
    @Column(name = "rentalpointidbeg", nullable = false, updatable = false)
    private Long rentalPointIdBeg;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rentalpointidbeg", referencedColumnName = "id", insertable = false, updatable = false)
    private RentalPoint rentalPointBeg;

    @Column(name = "rentalpointidend")
    private Long rentalPointIdEnd;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rentalpointidend", referencedColumnName = "id", insertable = false, updatable = false)
    private RentalPoint rentalPointEnd;

    @Version
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getDateBeg() {
        return dateBeg;
    }

    public void setDateBeg(Timestamp dateBeg) {
        this.dateBeg = dateBeg;
    }

    public Timestamp getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Timestamp dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Long getProgUserIdBeg() {
        return progUserIdBeg;
    }

    public void setProgUserIdBeg(Long progUserIdBeg) {
        this.progUserIdBeg = progUserIdBeg;
    }

    public ProgUser getProgUserBeg() {
        return progUserBeg;
    }

    public void setProgUserBeg(ProgUser progUserBeg) {
        this.progUserBeg = progUserBeg;
    }

    public Long getProgUserIdEnd() {
        return progUserIdEnd;
    }

    public void setProgUserIdEnd(Long progUserIdEnd) {
        this.progUserIdEnd = progUserIdEnd;
    }

    public ProgUser getProgUserEnd() {
        return progUserEnd;
    }

    public void setProgUserEnd(ProgUser progUserEnd) {
        this.progUserEnd = progUserEnd;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Long getRentalPointIdBeg() {
        return rentalPointIdBeg;
    }

    public void setRentalPointIdBeg(Long rentalPointIdBeg) {
        this.rentalPointIdBeg = rentalPointIdBeg;
    }

    public Long getRentalPointIdEnd() {
        return rentalPointIdEnd;
    }

    public void setRentalPointIdEnd(Long rentalPointIdEnd) {
        this.rentalPointIdEnd = rentalPointIdEnd;
    }

    public RentalPoint getRentalPointBeg() {
        return rentalPointBeg;
    }

    public void setRentalPointBeg(RentalPoint rentalPointBeg) {
        this.rentalPointBeg = rentalPointBeg;
    }

    public RentalPoint getRentalPointEnd() {
        return rentalPointEnd;
    }

    public void setRentalPointEnd(RentalPoint rentalPointEnd) {
        this.rentalPointEnd = rentalPointEnd;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
