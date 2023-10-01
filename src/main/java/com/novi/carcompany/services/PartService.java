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
        List<Part> fetchedParts = partRepository.findAll();
        List<PartDto> partDto = new ArrayList<>();

        if (!fetchedParts.isEmpty()) {

            for (Part part : fetchedParts) {
                PartDto dto = new PartDto();

                DtoConverters.partDtoConverter(part, dto);

                partDto.add(dto);
            }
                return partDto;
        } else {
            throw new RecordNotFoundException("We hebben helaas geen onderdelen in onze database gevonden.");
        }
    }

    ///// For fetching part by part number from database. /////
    public PartDto getPart(String partNumber) {

        if (partRepository.existsByPartNumberIgnoreCase(partNumber)) {
            PartDto dto = new PartDto();
            Part fetchedPart = partRepository.findByPartNumberIgnoreCase(partNumber).get();

            DtoConverters.partDtoConverter(fetchedPart, dto);

            return dto;
        } else {
            throw new RecordNotFoundException("We hebben geen onderdeel met nummer: " + partNumber + " in onze database.");
        }
    }

    /// For fetching parts by name from the database. /////
    public List<PartDto> getPartsByName(String name) {
        List<Part> fetchedParts = partRepository.findByNameContainsIgnoreCase(name);
        List<PartDto> dtoList = new ArrayList<>();

        if (!fetchedParts.isEmpty()) {

            for (Part part : fetchedParts) {
                PartDto dto = new PartDto();

                DtoConverters.partDtoConverter(part, dto);

                dtoList.add(dto);
            }
            return dtoList;
        } else {
            throw new RecordNotFoundException("We hebben geen onderdeel met naam: " + name + " gevonden in onze database.");
        }
    }




    ///// For fetching all parts currently in stock from the database. /////





}
