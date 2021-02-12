package com.ertelecom.carrental.service.impl;

import com.ertelecom.carrental.model.entity.Client;
import com.ertelecom.carrental.repository.ClientRepository;
import com.ertelecom.carrental.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("IClientService")
public class ClientServiceImpl implements IClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> load(){
        List<Client> list = clientRepository.findAll();
        return list;
    }

    @Override
    public Optional<Client> loadById(long id){
        return clientRepository.findById(id);
    }

    @Override
    public Client save(Client client){
        clientRepository.save(client);
        return client;
    }

    @Override
    public boolean deleteById(long id){
        if (clientRepository.findById(id).isPresent()) {
            clientRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
