package com.novi.carcompany.services;


import com.novi.carcompany.dtos.CarDto;
import com.novi.carcompany.dtos.CarDtoConverter;
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
            CarDto dto = new CarDto();

            carDtoConverter(car, dto);

            carDto.add(dto);
        }
        if (carDto.isEmpty()) {
            throw new RecordNotFoundException("We hebben helaas geen auto's in onze database gevonden.");
        } else {
            return carDto;
        }
    }


    ///// For fetching one car by licence plate from database /////
    public CarDto getCar(String licensePlate) {

        if (carRepository.findByLicensePlate(licensePlate).isPresent()) {
            CarDto dto = new CarDto();
            Car fetchedCar = carRepository.findByLicensePlate(licensePlate).get();

            carDtoConverter(fetchedCar, dto);

            return dto;
        } else {
            throw new RecordNotFoundException("We hebben geen auto met kenteken: " + licensePlate + " in onze database");
        }

    }



    private void carDtoConverter(Car car, CarDto dto) {
        dto.licensePlate = car.getLicensePlate();
        dto.brand = car.getBrand();
        dto.model = car.getModel();
        dto.vinNumber = car.getVinNumber();
        dto.color = car.getColor();
        dto.engine = car.getEngine();
        dto.winterTyres = car.getWinterTyres();
    }
}
