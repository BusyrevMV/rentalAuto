package com.ertelecom.carrental.service.impl;

import com.ertelecom.carrental.model.entity.CarBrand;
import com.ertelecom.carrental.repository.CarBrandRepository;
import com.ertelecom.carrental.service.ICarBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("ICarBrand")
public class CarBrandServiceImpl implements ICarBrandService {
    @Autowired
    private CarBrandRepository carBrandRepository;

    @Override
    public List<CarBrand> load(){
        List<CarBrand> list = carBrandRepository.findAll();
        return list;
    }

    @Override
    public Optional<CarBrand> loadById(long id){
        return carBrandRepository.findById(id);
    }

    @Override
    public CarBrand save(CarBrand carBrand){
        carBrandRepository.save(carBrand);
        return carBrand;
    }

    @Override
    public boolean deleteById(long id){
        if (carBrandRepository.findById(id).isPresent()) {
            carBrandRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
