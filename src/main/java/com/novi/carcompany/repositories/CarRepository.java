package com.novi.carcompany.repositories;


import com.novi.carcompany.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CarRepository extends JpaRepository<Car, String> {
    Optional<Car> findByLicensePlate(String licensePlate);

}
