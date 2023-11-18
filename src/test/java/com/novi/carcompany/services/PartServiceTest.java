package com.novi.carcompany.services;

import com.novi.carcompany.dtos.businessEntities.NumberInputDto;
import com.novi.carcompany.dtos.businessEntities.PartChangeInputDto;
import com.novi.carcompany.dtos.businessEntities.PartDto;
import com.novi.carcompany.dtos.businessEntities.PartInputDto;
import com.novi.carcompany.exceptions.AlreadyExistsException;
import com.novi.carcompany.exceptions.IllegalChangeException;
import com.novi.carcompany.exceptions.RecordNotFoundException;
import com.novi.carcompany.helpers.DtoConverters;
import com.novi.carcompany.models.businessEntities.FileDocument;
import com.novi.carcompany.models.businessEntities.Part;
import com.novi.carcompany.repositories.DocFileRepository;
import com.novi.carcompany.repositories.PartRepository;
import com.novi.carcompany.services.businessEntities.PartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class PartServiceTest {

    @Mock
    private PartRepository partRepository;

    @Mock
    private DocFileRepository fileDocumentRepository;

    @InjectMocks
    private PartService partService;

    @Captor
    private ArgumentCaptor<Part> partArgumentCaptor;


    Part partOne = new Part();
    Part partTwo = new Part();

    PartDto partDto = new PartDto();

    PartInputDto partInputDto = new PartInputDto();

    NumberInputDto numberInputDto = new NumberInputDto();

    PartChangeInputDto partChangeInputDto = new PartChangeInputDto();

    FileDocument pickNote = new FileDocument();


    @BeforeEach
    void setUp() {
        partOne.setPartNumber("11111");
        partOne.setName("Test one");
        partOne.setDescription("Test onderdeel");
        partOne.setLocation("A1-01");
        partOne.setStock(2);
        partOne.setPurchasePrice(10.25);
        partOne.setSellingPrice(15.35);
        /////
        partTwo.setPartNumber("22222");
        partTwo.setName("Test two");
        partTwo.setDescription("Test onderdeel");
        partTwo.setLocation("A2-02");
        partTwo.setStock(5);
        partTwo.setPurchasePrice(5.0);
        partTwo.setSellingPrice(10.50);
        /////
        partDto.partNumber = "11111";
        partDto.name = "Test one";
        partDto.description = "Test onderdeel";
        partDto.location = "A1-01";
        partDto.stock = 2;
        partDto.purchasePrice = 10.25;
        partDto.sellingPrice = 15.35;
        /////
        partInputDto.partNumber = "11111";
        partInputDto.name = "Test one";
        partInputDto.description = "Test onderdeel";
        partInputDto.location = "A1-01";
        partInputDto.stock = 2;
        partInputDto.purchasePrice = 10.25;
        partInputDto.sellingPrice = 15.35;
        /////
        partChangeInputDto.partNumber = "11111";
        partChangeInputDto.name = "Test one";
        partChangeInputDto.description = "Test onderdeel";
        partChangeInputDto.location = "A1-01";
        partChangeInputDto.quantity = 2;
        partChangeInputDto.purchasePrice = 10.25;
        partChangeInputDto.sellingPrice = 15.35;
        /////
        pickNote.setFileName("Test picknote");
        /////
        numberInputDto.id = 1L;
    }

    @Test
    @DisplayName("Should get all parts")
    void getParts() {
        given(partRepository.findAll()).willReturn(List.of(partOne, partTwo));

        List<Part> parts = partRepository.findAll();
        List<PartDto> partDtos = partService.getParts();

        assertEquals(parts.get(0).getPartNumber(), partDtos.get(0).partNumber);
        assertEquals(parts.get(0).getName(), partDtos.get(0).name);
        assertEquals(parts.get(0).getDescription(), partDtos.get(0).description);
        assertEquals(parts.get(0).getLocation(), partDtos.get(0).location);
        assertEquals(parts.get(0).getStock(), partDtos.get(0).stock);
        assertEquals(parts.get(0).getPurchasePrice(), partDtos.get(0).purchasePrice);
        assertEquals(parts.get(0).getSellingPrice(), partDtos.get(0).sellingPrice);
        /////
        assertEquals(parts.get(1).getPartNumber(), partDtos.get(1).partNumber);
        assertEquals(parts.get(1).getName(), partDtos.get(1).name);
        assertEquals(parts.get(1).getDescription(), partDtos.get(1).description);
        assertEquals(parts.get(1).getLocation(), partDtos.get(1).location);
        assertEquals(parts.get(1).getStock(), partDtos.get(1).stock);
        assertEquals(parts.get(1).getPurchasePrice(), partDtos.get(1).purchasePrice);
        assertEquals(parts.get(1).getSellingPrice(), partDtos.get(1).sellingPrice);
    }

    @Test
    @DisplayName("Should throw exception when database has no parts")
    void getPartsException() {
        given(partRepository.findAll()).willReturn(List.of());

        assertThrows(RecordNotFoundException.class, () -> partService.getParts());
    }

    @Test
    @DisplayName("Should get part by part number")
    void getPart() {
        given(partRepository.findByPartNumberIgnoreCase("11111")).willReturn(Optional.of(partOne));
        given(partRepository.existsByPartNumberIgnoreCase("11111")).willReturn(true);

        Part part = partRepository.findByPartNumberIgnoreCase("11111").get();
        PartDto partDto = partService.getPart("11111");

        assertEquals(part.getPartNumber(), partDto.partNumber);
        assertEquals(part.getName(), partDto.name);
        assertEquals(part.getDescription(), partDto.description);
        assertEquals(part.getLocation(), partDto.location);
        assertEquals(part.getStock(), partDto.stock);
        assertEquals(part.getPurchasePrice(), partDto.purchasePrice);
        assertEquals(part.getSellingPrice(), partDto.sellingPrice);
    }

    @Test
    @DisplayName("Should throw exception if part number is not in database")
    void getPartException() {
        assertThrows(RecordNotFoundException.class, () -> partService.getPart("11111"));
    }

    @Test
    @DisplayName("Should get cars by part name")
    void getPartsByName() {
        given(partRepository.findByNameContainsIgnoreCase("Test one")).willReturn(List.of(partOne));

        List<Part> parts = partRepository.findByNameContainsIgnoreCase("Test one");
        List<PartDto> partDtos = partService.getPartsByName("Test one");

        assertEquals(parts.get(0).getPartNumber(), partDtos.get(0).partNumber);
        assertEquals(parts.get(0).getName(), partDtos.get(0).name);
        assertEquals(parts.get(0).getDescription(), partDtos.get(0).description);
        assertEquals(parts.get(0).getLocation(), partDtos.get(0).location);
        assertEquals(parts.get(0).getStock(), partDtos.get(0).stock);
        assertEquals(parts.get(0).getPurchasePrice(), partDtos.get(0).purchasePrice);
        assertEquals(parts.get(0).getSellingPrice(), partDtos.get(0).sellingPrice);
    }

    @Test
    @DisplayName("Should throw exception if part name is not in database")
    void getPartsByNameException() {
        assertThrows(RecordNotFoundException.class, () -> partService.getPartsByName("Test one"));
    }

    @Test
    @DisplayName("Should get all parts on stock")
    void getPartsOnStock() {
        given(partRepository.findAll()).willReturn(List.of(partOne, partTwo));

        List<Part> parts = partRepository.findAll();
        List<PartDto> partDtos = partService.getPartsOnStock();

        assertEquals(parts.get(0).getPartNumber(), partDtos.get(0).partNumber);
        assertEquals(parts.get(0).getName(), partDtos.get(0).name);
        assertEquals(parts.get(0).getDescription(), partDtos.get(0).description);
        assertEquals(parts.get(0).getLocation(), partDtos.get(0).location);
        assertEquals(parts.get(0).getStock(), partDtos.get(0).stock);
        assertEquals(parts.get(0).getPurchasePrice(), partDtos.get(0).purchasePrice);
        assertEquals(parts.get(0).getSellingPrice(), partDtos.get(0).sellingPrice);
        /////
        assertEquals(parts.get(1).getPartNumber(), partDtos.get(1).partNumber);
        assertEquals(parts.get(1).getName(), partDtos.get(1).name);
        assertEquals(parts.get(1).getDescription(), partDtos.get(1).description);
        assertEquals(parts.get(1).getLocation(), partDtos.get(1).location);
        assertEquals(parts.get(1).getStock(), partDtos.get(1).stock);
        assertEquals(parts.get(1).getPurchasePrice(), partDtos.get(1).purchasePrice);
        assertEquals(parts.get(1).getSellingPrice(), partDtos.get(1).sellingPrice);
    }

    @Test
    @DisplayName("Should throw exception if there are no parts in database")
    void getPartsOnStockNoPartsException() {
        assertThrows(RecordNotFoundException.class, () -> partService.getPartsOnStock());
    }

    @Test
    @DisplayName("Should throw exception if there are no parts on stock")
    void getPartsOnStockNoStockException() {
        given(partRepository.findAll()).willReturn(List.of(partOne));
        partOne.setStock(0);
        assertThrows(RecordNotFoundException.class, () -> partService.getPartsOnStock());
    }

    @Test
    @DisplayName("Should create part")
    void createPart() {
        given(partRepository.findByPartNumberIgnoreCase("11111")).willReturn(Optional.of(partOne));

        Part newPart = new Part();
        DtoConverters.partInputDtoConverter(newPart, partInputDto);
        given(partRepository.save(newPart)).willReturn(partOne);

        partService.createPart(partInputDto);

        verify(partRepository, times(1)).save(partArgumentCaptor.capture());

        Part captured = partArgumentCaptor.getValue();

        assertEquals(partInputDto.partNumber, captured.getPartNumber());
        assertEquals(partInputDto.name, captured.getName());
        assertEquals(partInputDto.description, captured.getDescription());
        assertEquals(partInputDto.location, captured.getLocation());
        assertEquals(partInputDto.stock, captured.getStock());
        assertEquals(partInputDto.purchasePrice, captured.getPurchasePrice());
        assertEquals(partInputDto.sellingPrice, captured.getSellingPrice());
    }

    @Test
    @DisplayName("Should throw exception if part number is already in database")
    void createPartException() {
        Part newPart = new Part();
        DtoConverters.partInputDtoConverter(newPart, partInputDto);

        given(partRepository.existsByPartNumberIgnoreCase(newPart.getPartNumber())).willReturn(true);

        assertThrows(AlreadyExistsException.class, () -> partService.createPart(partInputDto));
    }

    @Test
    @DisplayName("Should change part")
    void changePart() {
        given(partRepository.findByPartNumberIgnoreCase("11111")).willReturn(Optional.of(partOne));
        given(partRepository.save(partOne)).willReturn(partOne);

        partService.changePart("11111", partChangeInputDto);

        verify(partRepository, times(1)).save(partArgumentCaptor.capture());

        Part captured = partArgumentCaptor.getValue();

        assertEquals(partInputDto.partNumber, captured.getPartNumber());
        assertEquals(partInputDto.name, captured.getName());
        assertEquals(partInputDto.description, captured.getDescription());
        assertEquals(partInputDto.location, captured.getLocation());
        assertEquals(partInputDto.stock + partChangeInputDto.quantity, captured.getStock());
        assertEquals(partInputDto.purchasePrice, captured.getPurchasePrice());
        assertEquals(partInputDto.sellingPrice, captured.getSellingPrice());
    }

    @Test
    @DisplayName("Should throw exception if part number is to be changed")
    void changePartExceptionChangePartNumber() {
        given(partRepository.findByPartNumberIgnoreCase("22222")).willReturn(Optional.of(partTwo));

        assertThrows(IllegalChangeException.class, () -> partService.changePart("22222", partChangeInputDto));
    }

    @Test
    @DisplayName("Should throw exception if part number is not in database")
    void changePartExceptionPartNumberUnknown() {
        assertThrows(RecordNotFoundException.class, () -> partService.changePart("11111", partChangeInputDto));
    }

    @Test
    @DisplayName("Should delete part")
    void deletePart() {
        given(partRepository.existsByPartNumberIgnoreCase("11111")).willReturn(true);

        partService.deletePart("11111");

        verify(partRepository, times(1)).deletePartByPartNumberIgnoreCase("11111");
    }

    @Test
    @DisplayName("Should throw exception if part number is not in database")
    void deletePartExceptionPartNumberUnknown() {
        assertThrows(RecordNotFoundException.class, () -> partService.deletePart("11111"));
    }

    @Test
    @DisplayName("Should assign picknote to part")
    void assignPicknoteToPartTest() {
        given(partRepository.findByPartNumberIgnoreCase("11111")).willReturn(Optional.of(partOne));
        given(fileDocumentRepository.findById(1L)).willReturn(Optional.of(pickNote));

        partService.assignPicknoteToPart("11111", numberInputDto);

        verify(partRepository, times(1)).save(partArgumentCaptor.capture());

        Part captured = partArgumentCaptor.getValue();

        assertEquals(pickNote, captured.getPicknote());
    }

    @Test
    @DisplayName("Should throw exception if part number is not in database")
    void assignPicknoteToPartExceptionPartNumberUnknown() {
        given(partRepository.findByPartNumberIgnoreCase("111")).willReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> partService.assignPicknoteToPart("111", numberInputDto));
    }

    @Test
    @DisplayName("Should throw exception if picknote is not in database")
    void assignPicknoteToPartExceptionPicknoteUnknown() {
        given(partRepository.findByPartNumberIgnoreCase("11111")).willReturn(Optional.of(partOne));
        given(fileDocumentRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> partService.assignPicknoteToPart("11111", numberInputDto));
    }
}