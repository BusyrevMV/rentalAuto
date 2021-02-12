package com.ertelecom.carrental.service.impl;

import com.ertelecom.carrental.model.entity.CarModel;
import com.ertelecom.carrental.repository.CarModelRepository;
import com.ertelecom.carrental.service.ICarModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("ICarModel")
public class CarModelServiceImpl implements ICarModelService {
    @Autowired
    private CarModelRepository carModelRepository;

    @Override
    public List<CarModel> load(){
        List<CarModel> list = carModelRepository.findAll();
        return list;
    }

    @Override
    public Optional<CarModel> loadById(long id){
        return carModelRepository.findById(id);
    }

    @Override
    public CarModel save(CarModel carModel){
        carModelRepository.save(carModel);
        return carModel;
    }

    @Override
    public boolean deleteById(long id){
        if (carModelRepository.findById(id).isPresent()) {
            carModelRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
