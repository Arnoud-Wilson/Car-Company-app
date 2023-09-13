package com.novi.carcompany.controllers;


import com.novi.carcompany.dtos.CarDto;
import com.novi.carcompany.repositories.CarRepository;
import com.novi.carcompany.services.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/{licensePlate}")
    public ResponseEntity<CarDto> getCar(@PathVariable String licensePlate) {
        return ResponseEntity.ok(carService.getCar(licensePlate));
    }



}



