package com.novi.carcompany.services;

import com.novi.carcompany.dtos.businessEntities.CarDto;
import com.novi.carcompany.dtos.businessEntities.CarInputDto;
import com.novi.carcompany.dtos.businessEntities.StringInputDto;
import com.novi.carcompany.exceptions.AlreadyExistsException;
import com.novi.carcompany.exceptions.IllegalChangeException;
import com.novi.carcompany.exceptions.RecordNotFoundException;
import com.novi.carcompany.helpers.DtoConverters;
import com.novi.carcompany.models.businessEntities.Car;
import com.novi.carcompany.models.businessEntities.Customer;
import com.novi.carcompany.repositories.CarRepository;
import com.novi.carcompany.repositories.CustomerRepository;
import com.novi.carcompany.services.businessEntities.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CarService carService;

    @Captor
    private ArgumentCaptor<Car> carArgumentCaptor;

    Car carOne = new Car();
    Car carTwo = new Car();

    CarDto carDtoOne = new CarDto();

    CarInputDto carInputDto = new CarInputDto();

    StringInputDto stringInputDto = new StringInputDto();

    Customer customerOne = new Customer();



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
        /////
        carInputDto.licensePlate = "NL-01-NL";
        carInputDto.brand = "Test";
        carInputDto.model = "Test1";
        carInputDto.vinNumber = "1111111111";
        carInputDto.color = "blue";
        carInputDto.engine = "2.5";
        carInputDto.winterTyres = false;
        /////
        carDtoOne.licensePlate = "NL-01-NL";
        carDtoOne.brand = "Test";
        carDtoOne.model = "Test1";
        carDtoOne.vinNumber = "1111111111";
        carDtoOne.color = "blue";
        carDtoOne.engine = "2.5";
        carDtoOne.winterTyres = false;
        /////
        customerOne.setBankAccount("NL01BANK01");
        customerOne.setCorporate(true);
        /////
        stringInputDto.id = "NL-01-NL";
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
        given(carRepository.findByLicensePlateIgnoreCase("NL-01-NL")).willReturn(Optional.of(carOne));

        Car newCar = new Car();
        DtoConverters.carInputDtoConverter(newCar, carInputDto);
        given(carRepository.save(newCar)).willReturn(carOne);

        carService.createCar(carInputDto);

        verify(carRepository, times(1)).save(carArgumentCaptor.capture());

        Car captured = carArgumentCaptor.getValue();

        assertEquals(carInputDto.licensePlate, captured.getLicensePlate());
        assertEquals(carInputDto.brand, captured.getBrand());
        assertEquals(carInputDto.model, captured.getModel());
        assertEquals(carInputDto.vinNumber, captured.getVinNumber());
        assertEquals(carInputDto.color, captured.getColor());
        assertEquals(carInputDto.engine, captured.getEngine());
        assertEquals(carInputDto.winterTyres, captured.getWinterTyres());
    }

    @Test
    @DisplayName("Should throw exception if license plate is already in database")
    void createCarException() {
        Car newCar = new Car();
        DtoConverters.carInputDtoConverter(newCar, carInputDto);

        given(carRepository.existsByLicensePlateIgnoreCase(newCar.getLicensePlate())).willReturn(true);

        assertThrows(AlreadyExistsException.class, () -> carService.createCar(carInputDto));
    }

    @Test
    @DisplayName("Should change car")
    void changeCar() {
        given(carRepository.findByLicensePlateIgnoreCase("NL-01-NL")).willReturn(Optional.of(carOne));
        given(carRepository.save(carOne)).willReturn(carOne);

        carService.changeCar("NL-01-NL", carDtoOne);

        verify(carRepository, times(1)).save(carArgumentCaptor.capture());

        Car captured = carArgumentCaptor.getValue();

        assertEquals(carDtoOne.licensePlate, captured.getLicensePlate());
        assertEquals(carDtoOne.brand, captured.getBrand());
        assertEquals(carDtoOne.model, captured.getModel());
        assertEquals(carDtoOne.vinNumber, captured.getVinNumber());
        assertEquals(carDtoOne.color, captured.getColor());
        assertEquals(carDtoOne.engine, captured.getEngine());
        assertEquals(carDtoOne.winterTyres, captured.getWinterTyres());
    }

    @Test
    @DisplayName("Should throw exception if license plate is to be changed")
    void changeCarExceptionChangeLicensePlate() {
        given(carRepository.findByLicensePlateIgnoreCase("NL-02-NL")).willReturn(Optional.of(carTwo));

            assertThrows(IllegalChangeException.class, () -> carService.changeCar("NL-02-NL", carDtoOne));
    }

    @Test
    @DisplayName("Should throw exception if license plate is not in database")
    void changeCarExceptionLicensePlateUnknown() {
        assertThrows(RecordNotFoundException.class, () -> carService.changeCar("NL-01-NL", carDtoOne));
    }

    @Test
    @DisplayName("Should delete car")
    void deleteCar() {
        given(carRepository.existsByLicensePlateIgnoreCase("NL-01-NL")).willReturn(true);

        carService.deleteCar("NL-01-NL");

        verify(carRepository, times(1)).deleteCarByLicensePlateIgnoreCase("NL-01-NL");
    }

    @Test
    @DisplayName("Should throw exception if license plate is not in database")
    void deleteCarExceptionLicensePlateUnknown() {
        assertThrows(RecordNotFoundException.class, () -> carService.deleteCar("NL-01-NL"));
    }

    @Test
    @DisplayName("Should assign car to customer")
    void assignCarToCustomerTest() {
        given(carRepository.findByLicensePlateIgnoreCase("NL-01-NL")).willReturn(Optional.of(carOne));
        given(customerRepository.findById(1L)).willReturn(Optional.of(customerOne));

        carService.assignCarsToCustomer(1L, stringInputDto);

        verify(carRepository, times(1)).save(carArgumentCaptor.capture());

        Car captured = carArgumentCaptor.getValue();

        assertEquals(stringInputDto.id, captured.getLicensePlate());
    }

    @Test
    @DisplayName("Should throw exception if licenseplate is not in database")
    void assignCarToCustomerExceptionLicenseplateUnknown() {
        given(customerRepository.findById(1L)).willReturn(Optional.of(customerOne));
        given(carRepository.findByLicensePlateIgnoreCase("NL-01-NL")).willReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> carService.assignCarsToCustomer(1L, stringInputDto));
    }

    @Test
    @DisplayName("Should throw exception if customer is not in database")
    void assignCarToCustomerExceptionCustomerUnknown() {
        given(customerRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> carService.assignCarsToCustomer(1L, stringInputDto));
    }
}