package com.ertelecom.carrental.service.impl;

import com.ertelecom.carrental.model.entity.ProgUser;
import com.ertelecom.carrental.repository.ProgUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgUserServiceImpl {
    @Autowired
    private ProgUserRepository progUserRepository;

    public List<ProgUser> load(){
        List<ProgUser> list = progUserRepository.findAll();
        return list;
    }

    public ProgUser save(ProgUser progUser){
        progUserRepository.save(progUser);
        return progUser;
    }

    public boolean deleteById(long id){
        if (progUserRepository.findById(id).isPresent()) {
            progUserRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
