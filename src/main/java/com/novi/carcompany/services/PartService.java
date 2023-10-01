package com.novi.carcompany.services;

import com.novi.carcompany.dtos.PartChangeInputDto;
import com.novi.carcompany.dtos.PartDto;
import com.novi.carcompany.dtos.PartInputDto;
import com.novi.carcompany.exceptions.AlreadyExistsException;
import com.novi.carcompany.exceptions.IllegalChangeException;
import com.novi.carcompany.exceptions.RecordNotFoundException;
import com.novi.carcompany.helpers.DtoConverters;
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
    public List<PartDto> getPartsOnStock() {
        List<Part> fetchedParts = partRepository.findAll();
        List<PartDto> dtoList = new ArrayList<>();

        if (!fetchedParts.isEmpty()) {
            for (Part part : fetchedParts) {
                if (part.getStock() >= 1) {
                    PartDto dto = new PartDto();

                    DtoConverters.partDtoConverter(part, dto);

                    dtoList.add(dto);
                }
            } if (dtoList.isEmpty()) {
                throw new RecordNotFoundException("We hebben geen onderdelen op voorraad.");
            } else {
                return dtoList;
            }
        } else {
            throw new RecordNotFoundException("We hebben geen onderdelen in onze database.");
        }
    }


    ///// Fot adding a part to the database. /////
    public PartDto createPart(PartInputDto part) {
        Part newPart = new Part();
        PartDto returnPart = new PartDto();

        DtoConverters.partInputDtoConverter(newPart, part);

        if (partRepository.existsByPartNumberIgnoreCase(newPart.getPartNumber())) {
            throw new AlreadyExistsException("We hebben al een onderdeel met nummer: " + newPart.getPartNumber() + " in onze database.");
        } else {
            partRepository.save(newPart);

            Part fetchedPart = partRepository.findByPartNumberIgnoreCase(newPart.getPartNumber()).get();
            DtoConverters.partDtoConverter(fetchedPart, returnPart);

            return returnPart;
        }
    }


    ///// For changing a part in the database. /////
    public PartDto changePart(String partNumber, PartChangeInputDto part) {
        Optional<Part> fetchedPart = partRepository.findByPartNumberIgnoreCase(partNumber);
        PartDto returnPart = new PartDto();

        if (fetchedPart.isPresent()) {
            Part partOne = fetchedPart.get();
            if (partNumber.equalsIgnoreCase(part.partNumber) || part.partNumber == null) {
                if (part.partNumber != null) {
                    partOne.setPartNumber(part.partNumber.toUpperCase());
                }
                if (part.name != null) {
                    partOne.setName(part.name);
                }
                if (part.description != null) {
                    partOne.setDescription(part.description);
                }
                if (part.location != null) {
                    partOne.setLocation(part.location);
                }

                partOne.setStock(partOne.getStock() + part.quantity);

                if (part.purchasePrice != null) {
                    partOne.setPurchasePrice(part.purchasePrice);
                }
                if (part.sellingPrice != null) {
                    partOne.setSellingPrice(part.sellingPrice);
                }

                partRepository.save(partOne);

                Part changedPart = partRepository.findByPartNumberIgnoreCase(partNumber).get();
                DtoConverters.partDtoConverter(changedPart, returnPart);

                return returnPart;

            } else {
                throw new IllegalChangeException("Het is niet mogelijk om het onderdeel nummer aan te passen.");
            }
        } else {
            throw new RecordNotFoundException("We hebben geen onderdeel met nummer: " + partNumber.toUpperCase() + " in onze database.");
        }
    }


    ///// For deleting a part from the database. /////
    public String deletePart(String partNumber) {
        if (partRepository.existsByPartNumberIgnoreCase(partNumber)) {

            partRepository.deleteCarByPartNumberIgnoreCase(partNumber);

            return "We hebben het onderdeel met nummer: " + partNumber.toUpperCase() + " succesvol verwijderd.";
        } else {
            throw new RecordNotFoundException("We hebben geen onderdeel met nummer: " + partNumber.toUpperCase() + " in onze database.");
        }
    }
}
