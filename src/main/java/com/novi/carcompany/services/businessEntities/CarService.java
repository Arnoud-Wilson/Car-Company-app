package com.novi.carcompany.services.businessEntities;

import com.novi.carcompany.dtos.businessEntities.CarDto;
import com.novi.carcompany.dtos.businessEntities.CarInputDto;
import com.novi.carcompany.dtos.businessEntities.CustomerDto;
import com.novi.carcompany.dtos.businessEntities.StringInputDto;
import com.novi.carcompany.exceptions.AlreadyExistsException;
import com.novi.carcompany.exceptions.IllegalChangeException;
import com.novi.carcompany.exceptions.RecordNotFoundException;
import com.novi.carcompany.helpers.DtoConverters;
import com.novi.carcompany.models.businessEntities.Car;
import com.novi.carcompany.models.businessEntities.Customer;
import com.novi.carcompany.repositories.CarRepository;
import com.novi.carcompany.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CarService implements ServiceStringIdInterface<CarDto, CarInputDto>  {

    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;

    public CarService(CarRepository carRepository, CustomerRepository customerRepository) {
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
    }


    ///// For fetching all cars. /////
    @Override
    public List<CarDto> getAll() {
        List<Car> fetchedCars = carRepository.findAll();
        List<CarDto> carDto = new ArrayList<>();

        if (!fetchedCars.isEmpty()) {

            for (Car car : fetchedCars) {
                CarDto dto = new CarDto();

                DtoConverters.carDtoConverter(car, dto);

                carDto.add(dto);
            }
            return carDto;
        } else {
            throw new RecordNotFoundException("We hebben helaas geen auto's in onze database gevonden.");
        }
    }

    ///// For fetching one car by licence plate. /////
    @Override
    public CarDto getOne(String licensePlate) {

        if (carRepository.existsByLicensePlateIgnoreCase(licensePlate)) {
            CarDto dto = new CarDto();
            Car fetchedCar = carRepository.findByLicensePlateIgnoreCase(licensePlate).get();

            DtoConverters.carDtoConverter(fetchedCar, dto);

            return dto;
        } else {
            throw new RecordNotFoundException("We hebben geen auto met kenteken: " + licensePlate + " in onze database.");
        }
    }

    ///// For fetching car by vin number. /////
    public List<CarDto> findCarByVinNumber(String vinNumber) {
        if (carRepository.existsCarsByVinNumberEqualsIgnoreCase(vinNumber)) {
            List<Car> vinNumberList = carRepository.findCarsByVinNumberIgnoreCase(vinNumber);
            List<CarDto> vinDtoList = new ArrayList<>();

            for (Car car : vinNumberList) {
                CarDto carDto = new CarDto();

                DtoConverters.carDtoConverter(car, carDto);
                vinDtoList.add(carDto);
            }
            return vinDtoList;
        } else {
            throw new RecordNotFoundException("We hebben geen auto met dit chassis nummer: " + vinNumber + " in onze database.");
        }
    }

    /// For fetching cars by brand or brand and model. /////
    public List<CarDto> findCar(String brand, String model) {
        List<CarDto> carDtoList = new ArrayList<>();
        List<Car> fetchedList = carRepository.findCarsByBrandContainingIgnoreCaseAndModelContainingIgnoreCase(brand, model);

        if (!fetchedList.isEmpty()) {

            for (Car car : fetchedList) {
                CarDto carDto = new CarDto();

                DtoConverters.carDtoConverter(car, carDto);
                carDtoList.add(carDto);
            }
            return carDtoList;
        } else {
            throw new RecordNotFoundException("We hebben geen auto's gevonden in onze database.");
        }
    }

    ///// For adding new car. /////
    @Override
    public CarDto createNew(CarInputDto car) {
        Car newCar = new Car();
        CarDto returnCar = new CarDto();

        DtoConverters.carInputDtoConverter(newCar, car);

        if (carRepository.existsByLicensePlateIgnoreCase(newCar.getLicensePlate())) {
            throw new AlreadyExistsException("We hebben al een auto met kenteken: " + newCar.getLicensePlate() + " in onze database.");
        } else {
            carRepository.save(newCar);

            Car fetchedCar = carRepository.findByLicensePlateIgnoreCase(newCar.getLicensePlate()).get();
            DtoConverters.carDtoConverter(fetchedCar, returnCar);

            return returnCar;
        }
    }

    ///// For changing a car. /////
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
                DtoConverters.carDtoConverter(changedCar, returnCar);

                return returnCar;

            } else {
                throw new IllegalChangeException("Het is niet mogelijk om het kenteken aan te passen.");
            }
        } else {
            throw new RecordNotFoundException("We hebben geen auto met kenteken: " + licensePlate.toUpperCase() + " in onze database.");
        }
    }

    ///// For assigning cars to customer. /////
    public CustomerDto assignCarsToCustomer(Long customerId, StringInputDto licensePlate) {
        Optional<Customer> fetchedCustomer =  customerRepository.findById(customerId);
        Optional<Car> fetchedCar = carRepository.findByLicensePlateIgnoreCase(licensePlate.id);

        if (fetchedCustomer.isPresent()) {
            Customer customer = fetchedCustomer.get();
            if (fetchedCar.isPresent()) {
                Car car = fetchedCar.get();
                CustomerDto dto = new CustomerDto();

                car.setCustomer(customer);
                carRepository.save(car);

                Customer modifiedCustomer = customerRepository.findById(customerId).get();
                DtoConverters.customerDtoConverter(modifiedCustomer, dto);

                return dto;
            } else {
                throw new RecordNotFoundException("We hebben geen auto met kenteken: " + licensePlate.id + ".");
            }
        } else {
            throw new RecordNotFoundException("We hebben geen klant met id: " + customerId + ".");
        }
    }

    ///// For deleting a car by license plate. /////
    @Override
    public String deleteOne(String licensePlate) {
        if (carRepository.existsByLicensePlateIgnoreCase(licensePlate)) {

            carRepository.deleteCarByLicensePlateIgnoreCase(licensePlate);

            return "We hebben de auto met kenteken: " + licensePlate.toUpperCase() + " succesvol verwijderd.";
        } else {
            throw new RecordNotFoundException("We hebben geen auto met kenteken: " + licensePlate.toUpperCase() + " in onze database.");
        }
    }
}
