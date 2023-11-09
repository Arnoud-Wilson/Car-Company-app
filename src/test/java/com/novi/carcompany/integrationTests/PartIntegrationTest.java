package com.novi.carcompany.integrationTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.novi.carcompany.dtos.businessEntities.PartChangeInputDto;
import com.novi.carcompany.dtos.businessEntities.PartDto;
import com.novi.carcompany.dtos.businessEntities.PartInputDto;
import com.novi.carcompany.models.businessEntities.Part;
import com.novi.carcompany.repositories.PartRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PartIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PartRepository partRepository;


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


        partRepository.save(partOne);
        partRepository.save(partTwo);
    }

    @AfterEach
    public void tearDown() {
        partRepository.deleteAll();
    }


    @Test
    void getCars() throws Exception {
        mockMvc.perform(get("/parts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].partNumber").value("11111"))
                .andExpect(jsonPath("$[0].name").value("Test one"))
                .andExpect(jsonPath("$[0].description").value("Test onderdeel"))
                .andExpect(jsonPath("$[0].location").value("A1-01"))
                .andExpect(jsonPath("$[0].stock").value(2))
                .andExpect(jsonPath("$[0].purchasePrice").value(10.25))
                .andExpect(jsonPath("$[0].sellingPrice").value(15.35))
                //
                .andExpect(jsonPath("$[1].partNumber").value("22222"))
                .andExpect(jsonPath("$[1].name").value("Test two"))
                .andExpect(jsonPath("$[1].description").value("Test onderdeel"))
                .andExpect(jsonPath("$[1].location").value("A2-02"))
                .andExpect(jsonPath("$[1].stock").value(5))
                .andExpect(jsonPath("$[1].purchasePrice").value(5.0))
                .andExpect(jsonPath("$[1].sellingPrice").value(10.50));
    }

    @Test
    void getPart() throws Exception {
        mockMvc.perform(get("/parts/11111"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("partNumber").value("11111"))
                .andExpect(jsonPath("name").value("Test one"))
                .andExpect(jsonPath("description").value("Test onderdeel"))
                .andExpect(jsonPath("location").value("A1-01"))
                .andExpect(jsonPath("stock").value(2))
                .andExpect(jsonPath("purchasePrice").value(10.25))
                .andExpect(jsonPath("sellingPrice").value(15.35));
    }

    @Test
    void getPartsByName() throws Exception {
        mockMvc.perform(get("/parts/findName?name=Test one"))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].partNumber").value("11111"))
                .andExpect(jsonPath("$[0].name").value("Test one"))
                .andExpect(jsonPath("$[0].description").value("Test onderdeel"))
                .andExpect(jsonPath("$[0].location").value("A1-01"))
                .andExpect(jsonPath("$[0].stock").value(2))
                .andExpect(jsonPath("$[0].purchasePrice").value(10.25))
                .andExpect(jsonPath("$[0].sellingPrice").value(15.35));
    }

    @Test
    void getPartsOnStock() throws Exception {
        mockMvc.perform(get("/parts/onStock"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].partNumber").value("11111"))
                .andExpect(jsonPath("$[0].name").value("Test one"))
                .andExpect(jsonPath("$[0].description").value("Test onderdeel"))
                .andExpect(jsonPath("$[0].location").value("A1-01"))
                .andExpect(jsonPath("$[0].stock").value(2))
                .andExpect(jsonPath("$[0].purchasePrice").value(10.25))
                .andExpect(jsonPath("$[0].sellingPrice").value(15.35))
                //
                .andExpect(jsonPath("$[1].partNumber").value("22222"))
                .andExpect(jsonPath("$[1].name").value("Test two"))
                .andExpect(jsonPath("$[1].description").value("Test onderdeel"))
                .andExpect(jsonPath("$[1].location").value("A2-02"))
                .andExpect(jsonPath("$[1].stock").value(5))
                .andExpect(jsonPath("$[1].purchasePrice").value(5.0))
                .andExpect(jsonPath("$[1].sellingPrice").value(10.50));
    }

    @Test
    void createPart() throws Exception {
        partRepository.deleteAll();
        mockMvc.perform(post("/parts")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonInputDtoString(partInputDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("partNumber").value("11111"))
                .andExpect(jsonPath("name").value("Test one"))
                .andExpect(jsonPath("description").value("Test onderdeel"))
                .andExpect(jsonPath("location").value("A1-01"))
                .andExpect(jsonPath("stock").value(2))
                .andExpect(jsonPath("purchasePrice").value(10.25))
                .andExpect(jsonPath("sellingPrice").value(15.35));
    }

    @Test
    void changePart() throws Exception {
        mockMvc.perform(put("/parts/11111")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonChangeInputDtoString(partChangeInputDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("partNumber").value("11111"))
                .andExpect(jsonPath("name").value("Test one"))
                .andExpect(jsonPath("description").value("Test onderdeel"))
                .andExpect(jsonPath("location").value("A1-01"))
                .andExpect(jsonPath("stock").value(4))
                .andExpect(jsonPath("purchasePrice").value(10.25))
                .andExpect(jsonPath("sellingPrice").value(15.35));
    }

    @Test
    void deletePart() throws Exception {
        mockMvc.perform(delete("/parts/11111"))
                .andExpect(status().isOk());
    }


    private String asJsonInputDtoString(final PartInputDto obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String asJsonChangeInputDtoString(final PartChangeInputDto obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
