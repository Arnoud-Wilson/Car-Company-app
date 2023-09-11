package com.novi.carcompany.services;


import com.novi.carcompany.dtos.CarDto;
import com.novi.carcompany.exceptions.IndexOutOfBoundException;
import com.novi.carcompany.exceptions.RecordNotFoundException;
import com.novi.carcompany.models.Car;
import com.novi.carcompany.repositories.CarRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }


    ///// For fetching all cars currently in the database /////
    public List<CarDto> getCars() {
        List<Car> fetchedCars = new ArrayList<>(carRepository.findAll());
        List<CarDto> carDto = new ArrayList<>();

        for (Car car : fetchedCars) {
            CarDto dto = CarDto.fillCarDto(car);
            carDto.add(dto);
        }
        if (carDto.isEmpty()) {
            throw new RecordNotFoundException("We hebben helaas geen auto's in onze database gevonden.");
        } else {
            return carDto;
        }
    }
}
