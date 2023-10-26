package com.novi.carcompany.services;

import com.novi.carcompany.dtos.CarDto;
import com.novi.carcompany.dtos.PartChangeInputDto;
import com.novi.carcompany.dtos.PartDto;
import com.novi.carcompany.dtos.PartInputDto;
import com.novi.carcompany.exceptions.RecordNotFoundException;
import com.novi.carcompany.models.Car;
import com.novi.carcompany.models.Part;
import com.novi.carcompany.repositories.PartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PartServiceTest {

    //TODO: @WithMockUser(username="testuser", roles="USER")  // check authorization, not authentication (onder @test)
    //TODO: @AutoConfigureMockMvc(addFilters = false) (boven aan testclass)

    @Mock
    PartRepository partRepository;

    @InjectMocks
    PartService partService;

    @Captor
    ArgumentCaptor<Part> partArgumentCaptor;


    Part partOne = new Part();
    Part partTwo = new Part();

    PartDto partDto = new PartDto();

    PartInputDto partInputDto = new PartInputDto();

    PartChangeInputDto partChangeInputDto = new PartChangeInputDto();


    @BeforeEach
    void setUp() {
        partOne.setPartNumber("11111");
        partOne.setName("Test one");
        partOne.setDescription("Test onderdeel");
        partOne.setLocation("A1-01");
        partOne.setStock(2);
        partOne.setPurchasePrice(10.25);
        partOne.setSellingPrice(15.35);

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
    void getCarsException() {
        given(partRepository.findAll()).willReturn(List.of());

        assertThrows(RecordNotFoundException.class, () -> partService.getParts());
    }

    @Test
    void getPart() {
    }

    @Test
    void getPartsByName() {
    }

    @Test
    void getPartsOnStock() {
    }

    @Test
    void createPart() {
    }

    @Test
    void changePart() {
    }

    @Test
    void deletePart() {
    }
}