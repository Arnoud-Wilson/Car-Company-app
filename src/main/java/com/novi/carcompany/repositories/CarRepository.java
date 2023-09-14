package com.novi.carcompany.repositories;


import com.novi.carcompany.models.Car;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CarRepository extends JpaRepository<Car, String> {
    Optional<Car> findByLicensePlateIgnoreCase(String licensePlate);

    Boolean existsByLicensePlateIgnoreCase(String licensePlate);

    @Transactional
    void deleteCarByLicensePlateIgnoreCase(String licensePlate);

    Optional<List<Car>> findCarsByBrandContainingIgnoreCase(String brand);

    Boolean existsCarsByBrandContainingIgnoreCase(String brand);

    Optional<List<Car>> findCarsByVinNumberIgnoreCase(String vinNumber);

    Boolean existsCarsByVinNumberEqualsIgnoreCase(String vinNumber);

    Optional<List<Car>> findCarsByBrandContainingIgnoreCaseAndModelContainingIgnoreCase(String brand, String model);

}
