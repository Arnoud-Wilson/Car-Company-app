package com.novi.carcompany.services;

import com.novi.carcompany.dtos.CarDto;
import com.novi.carcompany.dtos.PartDto;
import com.novi.carcompany.exceptions.RecordNotFoundException;
import com.novi.carcompany.helpers.DtoConverters;
import com.novi.carcompany.models.Car;
import com.novi.carcompany.models.Part;
import com.novi.carcompany.repositories.PartRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PartService {

    private final PartRepository partRepository;

    public PartService(PartRepository partRepository) {
        this.partRepository = partRepository;
    }


    ///// For fetching all parts in the database. /////
    public List<PartDto> getParts(){
        List<Part> fetchedParts = new ArrayList<>(partRepository.findAll());
        List<PartDto> partDto = new ArrayList<>();

        for (Part part : fetchedParts) {
            PartDto dto = new PartDto();

            DtoConverters.partDtoConverter(part, dto);

            partDto.add(dto);
        }
        if (partDto.isEmpty()) {
            throw new RecordNotFoundException("We hebben helaas geen onderdelen in onze database gevonden.");
        } else {
            return partDto;
        }
    }





}
