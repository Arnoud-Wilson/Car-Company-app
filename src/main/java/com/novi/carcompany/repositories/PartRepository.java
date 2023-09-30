package com.novi.carcompany.repositories;

import com.novi.carcompany.models.Car;
import com.novi.carcompany.models.Part;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartRepository extends JpaRepository<Part, String> {

    Boolean existsByPartNumberIgnoreCase(String partNumber);
    Optional<Part> findByPartNumberIgnoreCase(String partNumber);

}
