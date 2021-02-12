package com.ertelecom.carrental.service;

import com.ertelecom.carrental.model.entity.Client;

import java.util.List;
import java.util.Optional;

public interface IClientService {
    List<Client> load();

    Optional<Client> loadById(long id);

    Client save(Client client);

    boolean deleteById(long id);
}
