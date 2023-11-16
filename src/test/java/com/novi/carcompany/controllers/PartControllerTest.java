package com.novi.carcompany.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.novi.carcompany.controllers.PartController;
import com.novi.carcompany.dtos.businessEntities.PartChangeInputDto;
import com.novi.carcompany.dtos.businessEntities.PartDto;
import com.novi.carcompany.dtos.businessEntities.PartInputDto;
import com.novi.carcompany.filters.JwtRequestFilter;
import com.novi.carcompany.services.businessEntities.PartService;
import com.novi.carcompany.services.security.CustomUserDetailsService;
import com.novi.carcompany.utilities.JwtUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(PartController.class)
@AutoConfigureMockMvc(addFilters = false)
class PartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtUtility jwtUtility;

    @MockBean
    private PartService partService;


    PartDto partDtoOne = new PartDto();
    PartDto partDtoTwo = new PartDto();

    PartInputDto partInputDto = new PartInputDto();

    PartChangeInputDto partChangeInputDto = new PartChangeInputDto();

    @BeforeEach
    void setUp() {
        partDtoOne.partNumber = "11111";
        partDtoOne.name = "Test one";
        partDtoOne.description = "Test onderdeel";
        partDtoOne.location = "A1-01";
        partDtoOne.stock = 2;
        partDtoOne.purchasePrice = 10.25;
        partDtoOne.sellingPrice = 15.35;

        partDtoTwo.partNumber = "22222";
        partDtoTwo.name = "Test two";
        partDtoTwo.description = "Test onderdeel";
        partDtoTwo.location = "A2-02";
        partDtoTwo.stock = 5;
        partDtoTwo.purchasePrice = 5.0;
        partDtoTwo.sellingPrice = 10.50;
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
    @DisplayName("should return all parts")
    void getCars() throws Exception {
        given(partService.getParts()).willReturn(List.of(partDtoOne, partDtoTwo));

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
    @DisplayName("Should return part by part number")
    void getPart() throws Exception {
        given(partService.getPart("11111")).willReturn(partDtoOne);

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
    @DisplayName("Should return part by name")
    void getPartsByName() throws Exception {
        given(partService.getPartsByName("Test one")).willReturn(Collections.singletonList(partDtoOne));

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
    @DisplayName("Should return all parts on stock")
    void getPartsOnStock() throws Exception {
        given(partService.getPartsOnStock()).willReturn(List.of(partDtoOne, partDtoTwo));

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
    @DisplayName("Should create and return a new part")
    void createPart() throws Exception {
        given(partService.createPart(any())).willReturn(partDtoOne);

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
    @DisplayName("Should change and return part")
    void changePart() throws Exception {
        given(partService.changePart("11111", partChangeInputDto)).willReturn(partDtoOne);

        mockMvc.perform(put("/parts/11111")
                    .contentType(APPLICATION_JSON)
                    .content(asJsonChangeInputDtoString(partChangeInputDto)))
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
    @DisplayName("Should delete part by part number")
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