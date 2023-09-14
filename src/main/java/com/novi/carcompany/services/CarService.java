package com.novi.carcompany.services;


import com.novi.carcompany.dtos.CarDto;
import com.novi.carcompany.dtos.CarInputDto;
import com.novi.carcompany.exceptions.AlreadyExistsException;
import com.novi.carcompany.exceptions.IllegalChangeException;
import com.novi.carcompany.exceptions.RecordNotFoundException;
import com.novi.carcompany.models.Car;
import com.novi.carcompany.repositories.CarRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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

        if (carRepository.existsByLicensePlateIgnoreCase(licensePlate)) {
            CarDto dto = new CarDto();
            Car fetchedCar = carRepository.findByLicensePlateIgnoreCase(licensePlate).get();

            carDtoConverter(fetchedCar, dto);

            return dto;
        } else {
            throw new RecordNotFoundException("We hebben geen auto met kenteken: " + licensePlate + " in onze database.");
        }
    }


    ///// For adding a car to the database /////
    public CarDto createCar(CarInputDto car) {
        Car newCar = new Car();
        CarDto returnCar = new CarDto();

        carInputDtoConverter(newCar, car);

        if (carRepository.existsByLicensePlateIgnoreCase(newCar.getLicensePlate())) {
            throw new AlreadyExistsException("We hebben al een auto met kenteken: " + newCar.getLicensePlate() + " in onze database.");
        } else {
            carRepository.save(newCar);

            Car fetchedCar = carRepository.findByLicensePlateIgnoreCase(newCar.getLicensePlate()).get();
            carDtoConverter(fetchedCar, returnCar);

            return returnCar;
        }
    }


    ///// For changing a car in the database /////
    public CarDto changeCar(String licensePlate, CarDto car) {
        Optional<Car> fetchedCar = carRepository.findByLicensePlateIgnoreCase(licensePlate);
        CarDto returnCar = new CarDto();

        if (fetchedCar.isPresent()) {
            Car car1 = fetchedCar.get();
            if (licensePlate.equalsIgnoreCase(car.licensePlate) || car.licensePlate == null) {
                if (car.brand != null) {
                    car1.setBrand(car.brand);
                }
                if (car.model != null) {
                    car1.setModel(car.model);
                }
                if (car.vinNumber != null) {
                    car1.setVinNumber(car.vinNumber.toUpperCase());
                }
                if (car.color != null) {
                    car1.setColor(car.color);
                }
                if (car.engine != null) {
                    car1.setEngine(car.engine);
                }
                if (car.winterTyres != null) {
                    car1.setWinterTyres(car.winterTyres);
                }

                carRepository.save(car1);

                Car changedCar = carRepository.findByLicensePlateIgnoreCase(licensePlate).get();
                carDtoConverter(changedCar, returnCar);

                return returnCar;

            } else {
                throw new IllegalChangeException("Het is niet mogelijk om het kenteken aan te passen.");
            }
        } else {
            throw new RecordNotFoundException("We hebben geen auto met kenteken: " + licensePlate.toUpperCase() + " in onze database.");
        }
    }


    ///// For deleting a car from the database /////
    public String deleteCar(String licensePlate) {
        if (carRepository.existsByLicensePlateIgnoreCase(licensePlate)) {

            carRepository.deleteCarByLicensePlateIgnoreCase(licensePlate);

            return "We hebben de auto met kenteken: " + licensePlate.toUpperCase() + " succesvol verwijderd.";
        } else {
            throw new RecordNotFoundException("We hebben geen auto met kenteken: " + licensePlate.toUpperCase() + " in onze database.");
        }
    }




    private void carInputDtoConverter(Car car, CarInputDto dto) {
        car.setLicensePlate(dto.licensePlate.toUpperCase());
        car.setBrand(dto.brand);
        car.setModel(dto.model);
        car.setVinNumber(dto.vinNumber.toUpperCase());
        car.setColor(dto.color);
        car.setEngine(dto.engine);
        car.setWinterTyres(dto.winterTyres);
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
