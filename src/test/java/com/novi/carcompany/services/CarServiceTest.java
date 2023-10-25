package com.novi.carcompany.services;

import com.novi.carcompany.dtos.CarDto;
import com.novi.carcompany.exceptions.RecordNotFoundException;
import com.novi.carcompany.models.Car;
import com.novi.carcompany.repositories.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    //TODO: @WithMockUser(username="testuser", roles="USER")  // check authorization, not authentication (onder @test)
    //TODO: @AutoConfigureMockMvc(addFilters = false) (boven aan testclass)

    @Mock
    CarRepository carRepository;

    @InjectMocks
    CarService carService;


    Car carOne = new Car();
    Car carTwo = new Car();


    @BeforeEach
    void setUp() {
        carOne.setLicensePlate("NL-01-NL");
        carOne.setBrand("Test");
        carOne.setModel("Test1");
        carOne.setVinNumber("1111111111");
        carOne.setColor("blue");
        carOne.setEngine("2.5");
        carOne.setWinterTyres(false);

        carTwo.setLicensePlate("NL-02-NL");
        carTwo.setBrand("Test");
        carTwo.setModel("Test2");
        carTwo.setVinNumber("2222222222");
        carTwo.setColor("red");
        carTwo.setEngine("2.0");
        carTwo.setWinterTyres(true);
    }

    @Test
    @DisplayName("Should get all cars")
    void getCars() {
        given(carRepository.findAll()).willReturn(List.of(carOne, carTwo));

        List<Car> cars = carRepository.findAll();
        List<CarDto> carDtos = carService.getCars();

        assertEquals(cars.get(0).getLicensePlate(), carDtos.get(0).licensePlate);
        assertEquals(cars.get(0).getBrand(), carDtos.get(0).brand);
        assertEquals(cars.get(0).getModel(), carDtos.get(0).model);
        assertEquals(cars.get(0).getVinNumber(), carDtos.get(0).vinNumber);
        assertEquals(cars.get(0).getColor(), carDtos.get(0).color);
        assertEquals(cars.get(0).getEngine(), carDtos.get(0).engine);
        assertEquals(cars.get(0).getWinterTyres(), carDtos.get(0).winterTyres);
        /////
        assertEquals(cars.get(1).getLicensePlate(), carDtos.get(1).licensePlate);
        assertEquals(cars.get(1).getBrand(), carDtos.get(1).brand);
        assertEquals(cars.get(1).getModel(), carDtos.get(1).model);
        assertEquals(cars.get(1).getVinNumber(), carDtos.get(1).vinNumber);
        assertEquals(cars.get(1).getColor(), carDtos.get(1).color);
        assertEquals(cars.get(1).getEngine(), carDtos.get(1).engine);
        assertEquals(cars.get(1).getWinterTyres(), carDtos.get(1).winterTyres);
    }

    @Test
    @DisplayName("Should throw exception when database has no cars")
    void getCarsException() {
        given(carRepository.findAll()).willReturn(List.of());

        assertThrows(RecordNotFoundException.class, () -> carService.getCars());
    }

    @Test
    @DisplayName("Should get car by license plate")
    void getCar() {
        given(carRepository.findByLicensePlateIgnoreCase("NL-01-NL")).willReturn(Optional.of(carOne));
        given(carRepository.existsByLicensePlateIgnoreCase("NL-01-NL")).willReturn(true);

        Car car = carRepository.findByLicensePlateIgnoreCase("NL-01-NL").get();
        CarDto carDto = carService.getCar("NL-01-NL");

        assertEquals(car.getLicensePlate(), carDto.licensePlate);
        assertEquals(car.getBrand(), carDto.brand);
        assertEquals(car.getModel(), carDto.model);
        assertEquals(car.getVinNumber(), carDto.vinNumber);
        assertEquals(car.getColor(), carDto.color);
        assertEquals(car.getEngine(), carDto.engine);
        assertEquals(car.getWinterTyres(), carDto.winterTyres);
    }

    @Test
    @DisplayName("Should throw exception if license plate is not in database")
    void getCarException() {
        assertThrows(RecordNotFoundException.class, () -> carService.getCar("NL-01-NL"));
    }

    @Test
    @DisplayName("Should get cars by vin number")
    void findCarByVinNumber() {
        given(carRepository.findCarsByVinNumberIgnoreCase("1111111111")).willReturn(List.of(carOne));
        given(carRepository.existsCarsByVinNumberEqualsIgnoreCase("1111111111")).willReturn(true);

        List<Car> cars = carRepository.findCarsByVinNumberIgnoreCase("1111111111");
        List<CarDto> carDtos = carService.findCarByVinNumber("1111111111");

        assertEquals(cars.get(0).getLicensePlate(), carDtos.get(0).licensePlate);
        assertEquals(cars.get(0).getBrand(), carDtos.get(0).brand);
        assertEquals(cars.get(0).getModel(), carDtos.get(0).model);
        assertEquals(cars.get(0).getVinNumber(), carDtos.get(0).vinNumber);
        assertEquals(cars.get(0).getColor(), carDtos.get(0).color);
        assertEquals(cars.get(0).getEngine(), carDtos.get(0).engine);
        assertEquals(cars.get(0).getWinterTyres(), carDtos.get(0).winterTyres);
    }

    @Test
    @DisplayName("Should throw exception if vin number is not in database")
    void findCarByVinNumberException() {
        assertThrows(RecordNotFoundException.class, () -> carService.findCarByVinNumber("1111111111"));
    }

    @Test
    @DisplayName("Should get cars by brand/model")
    void findCar() {
        given(carRepository.findCarsByBrandContainingIgnoreCaseAndModelContainingIgnoreCase("Test", "Test1")).willReturn(List.of(carOne, carTwo));

        List<Car> cars = carRepository.findCarsByBrandContainingIgnoreCaseAndModelContainingIgnoreCase("Test", "Test1");
        List<CarDto> carDtos = carService.findCar("Test", "Test1");

        assertEquals(cars.get(0).getLicensePlate(), carDtos.get(0).licensePlate);
        assertEquals(cars.get(0).getBrand(), carDtos.get(0).brand);
        assertEquals(cars.get(0).getModel(), carDtos.get(0).model);
        assertEquals(cars.get(0).getVinNumber(), carDtos.get(0).vinNumber);
        assertEquals(cars.get(0).getColor(), carDtos.get(0).color);
        assertEquals(cars.get(0).getEngine(), carDtos.get(0).engine);
        assertEquals(cars.get(0).getWinterTyres(), carDtos.get(0).winterTyres);
        /////
        assertEquals(cars.get(1).getLicensePlate(), carDtos.get(1).licensePlate);
        assertEquals(cars.get(1).getBrand(), carDtos.get(1).brand);
        assertEquals(cars.get(1).getModel(), carDtos.get(1).model);
        assertEquals(cars.get(1).getVinNumber(), carDtos.get(1).vinNumber);
        assertEquals(cars.get(1).getColor(), carDtos.get(1).color);
        assertEquals(cars.get(1).getEngine(), carDtos.get(1).engine);
        assertEquals(cars.get(1).getWinterTyres(), carDtos.get(1).winterTyres);
    }

    @Test
    @DisplayName("Should throw exception if brand/model is not in database")
    void findCarException() {
        assertThrows(RecordNotFoundException.class, () -> carService.findCar("Test", "Test1"));
    }

    @Test
    @DisplayName("Should create new car")
    void createCar() {
        given()
    }

    @Test
    void changeCar() {
    }

    @Test
    void deleteCar() {
    }
}