package com.ertelecom.carrental.model.view;

import com.ertelecom.carrental.model.entity.Client;

public class ClientView {
    public Long id;
    public String name;
    public String surname;
    public String patronymic;
    public String driverNumber;

    public ClientView() {}

    public ClientView(Client client){
        this.id = client.getId();
        this.name = client.getName();
        this.surname = client.getSurname();
        this.patronymic = client.getPatronymic();
        this.driverNumber = client.getDriverNumber();
    }
}
