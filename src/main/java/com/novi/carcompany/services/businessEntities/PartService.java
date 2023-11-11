package com.novi.carcompany.services.businessEntities;

import com.novi.carcompany.dtos.businessEntities.*;
import com.novi.carcompany.exceptions.AlreadyExistsException;
import com.novi.carcompany.exceptions.IllegalChangeException;
import com.novi.carcompany.exceptions.RecordNotFoundException;
import com.novi.carcompany.helpers.DtoConverters;
import com.novi.carcompany.models.businessEntities.Car;
import com.novi.carcompany.models.businessEntities.FileDocument;
import com.novi.carcompany.models.businessEntities.Invoice;
import com.novi.carcompany.models.businessEntities.Part;
import com.novi.carcompany.repositories.DocFileRepository;
import com.novi.carcompany.repositories.PartRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class PartService {

    private final PartRepository partRepository;
    private final DocFileRepository docFileRepository;

    public PartService(PartRepository partRepository, DocFileRepository docFileRepository) {
        this.partRepository = partRepository;
        this.docFileRepository = docFileRepository;
    }


    ///// For fetching all parts. /////
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

    ///// For fetching part by part number. /////
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

    /// For fetching parts by name. /////
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

    ///// For fetching all parts currently on stock. /////
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

    ///// For adding a new part. /////
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

    ///// For changing a part. /////
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

    ///// For deleting a part by part number. /////
    public String deletePart(String partNumber) {
        if (partRepository.existsByPartNumberIgnoreCase(partNumber)) {

            partRepository.deletePartByPartNumberIgnoreCase(partNumber);

            return "We hebben het onderdeel met nummer: " + partNumber.toUpperCase() + " succesvol verwijderd.";
        } else {
            throw new RecordNotFoundException("We hebben geen onderdeel met nummer: " + partNumber.toUpperCase() + " in onze database.");
        }
    }

    ///// Assign picknote to part. /////
    public PartDto assignPicknoteToPart(String partNumber, NumberInputDto picknote) {
        Optional<Part> fetchedPart =  partRepository.findByPartNumberIgnoreCase(partNumber);
        Optional<FileDocument> fetchedFile = docFileRepository.findById(picknote.id);

        if (fetchedPart.isPresent()) {
            Part part = fetchedPart.get();
            if (fetchedFile.isPresent()) {
                FileDocument picknote1 = fetchedFile.get();
                PartDto dto = new PartDto();

                part.setPicknote(picknote1);
                partRepository.save(part);

                Part modifiedPart = partRepository.findByPartNumberIgnoreCase(partNumber).get();
                DtoConverters.partDtoConverter(modifiedPart, dto);

                return dto;
            } else {
                throw new RecordNotFoundException("We hebben geen pakbon met id: " + picknote.id + ".");
            }
        } else {
            throw new RecordNotFoundException("We hebben geen onderdeel met nummer: " + partNumber + ".");
        }
    }
}
