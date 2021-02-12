package com.ertelecom.carrental.service.impl;

import com.ertelecom.carrental.model.entity.RentalPoint;
import com.ertelecom.carrental.repository.RentalPointRepository;
import com.ertelecom.carrental.service.IRentalPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("RentalPointService")
public class RentalPointServiceImpl implements IRentalPointService {
    @Autowired
    private RentalPointRepository rentalPointRepository;

    @Override
    public List<RentalPoint> load(){
        List<RentalPoint> list = rentalPointRepository.findAll();
        return list;
    }

    @Override
    public Optional<RentalPoint> loadById(long id){
        return rentalPointRepository.findById(id);
    }

    @Override
    public RentalPoint save(RentalPoint rentalPoint){
        rentalPointRepository.save(rentalPoint);
        return rentalPoint;
    }

    @Override
    public boolean deleteById(long id){
        if (rentalPointRepository.findById(id).isPresent()) {
            rentalPointRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
