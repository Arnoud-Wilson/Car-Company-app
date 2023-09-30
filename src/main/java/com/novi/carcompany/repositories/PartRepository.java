package com.novi.carcompany.repositories;

import com.novi.carcompany.models.Part;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartRepository extends JpaRepository<Part, String> {

}
