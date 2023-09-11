package com.novi.carcompany.repositories;


import com.novi.carcompany.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CarRepository extends JpaRepository<Car, String> {

}
