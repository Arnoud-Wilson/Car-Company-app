package com.novi.carcompany.repositories;

import com.novi.carcompany.models.businessEntities.Part;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface PartRepository extends JpaRepository<Part, String> {

    Boolean existsByPartNumberIgnoreCase(String partNumber);
    Optional<Part> findByPartNumberIgnoreCase(String partNumber);
    List<Part>findPartsByPartNumberIgnoreCase(String partNumber);
    @Transactional
    void deletePartByPartNumberIgnoreCase(String partNumber);
    List<Part> findByNameContainsIgnoreCase(String name);
}
