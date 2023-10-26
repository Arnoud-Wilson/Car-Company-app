package com.novi.carcompany.services;

import com.novi.carcompany.dtos.PartChangeInputDto;
import com.novi.carcompany.dtos.PartDto;
import com.novi.carcompany.dtos.PartInputDto;
import com.novi.carcompany.models.Part;
import com.novi.carcompany.repositories.PartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

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
    void getParts() {
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