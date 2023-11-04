package com.novi.carcompany.controllers;

import com.novi.carcompany.dtos.*;
import com.novi.carcompany.services.CarService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }


    @GetMapping
    public ResponseEntity<List<CarDto>> getCars() {
        return ResponseEntity.ok(carService.getCars());
    }

    @GetMapping("/{licensePlate}")
    public ResponseEntity<CarDto> getCar(@PathVariable String licensePlate) {
        return ResponseEntity.ok(carService.getCar(licensePlate));
    }

    @GetMapping("/findVin")
    public ResponseEntity<List<CarDto>> findCarByVinNumber(@RequestParam String vinNumber) {
        return ResponseEntity.ok().body(carService.findCarByVinNumber(vinNumber));
    }

    @GetMapping("/find")
    public ResponseEntity<List<CarDto>> searchCar(@RequestParam(required = false) String brand, @RequestParam(required = false) String model) {
        return ResponseEntity.ok().body(carService.findCar(brand, model));
        //TODO: only working with both params..
    }

    @PostMapping
    public ResponseEntity<Object> createCar(@Valid @RequestBody CarInputDto car, BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                stringBuilder.append(fieldError.getField());
                stringBuilder.append(": ");
                stringBuilder.append(fieldError.getDefaultMessage());
                stringBuilder.append("\n");
            }
            return ResponseEntity.badRequest().body(stringBuilder);
        } else {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + car.licensePlate.toUpperCase()).toUriString());

            return ResponseEntity.created(uri).body(carService.createCar(car));
        }
    }

    @PutMapping("/{licensePlate}")
    public ResponseEntity<CarDto> changeCar(@PathVariable String licensePlate, @RequestBody CarDto car) {
//TODO validate request body
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());

        return ResponseEntity.created(uri).body(carService.changeCar(licensePlate, car));
    }

    @PutMapping("/customer/{customerId}")
    ResponseEntity<Object> assignCarToCustomer(@PathVariable Long customerId, @Valid @RequestBody StringInputDto licensePlate, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                stringBuilder.append(fieldError.getField());
                stringBuilder.append(": ");
                stringBuilder.append(fieldError.getDefaultMessage());
                stringBuilder.append("\n");
            }
            return ResponseEntity.badRequest().body(stringBuilder);
        } else {

            CustomerDto dto = carService.assignCarsToCustomer(customerId, licensePlate);
            URI uri = URI.create("http://localhost:8080/cars/" + customerId);

            return ResponseEntity.created(uri).body(dto);
        }
    }

    @DeleteMapping("/{licensePlate}")
    public ResponseEntity<String> deleteCar(@PathVariable String licensePlate) {
        return ResponseEntity.ok(carService.deleteCar(licensePlate));
    }

    //TODO: get cars by customer id and assign customer to car.
}



