package com.ertelecom.carrental.model.view;

import com.ertelecom.carrental.model.entity.ProgUser;

public class ProgUserView {
    public Long id;
    public String name;
    public String surname;
    public String patronymic;
    public String login;

    public ProgUserView(){}

    public ProgUserView(ProgUser progUser){
        this.id = progUser.getId();
        this.name = progUser.getName();
        this.surname = progUser.getSurname();
        this.patronymic = progUser.getPatronymic();
        this.login = progUser.getLogin();
    }
}
