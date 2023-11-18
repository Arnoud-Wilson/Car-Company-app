package com.novi.carcompany.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.novi.carcompany.controllers.businessEntities.CarController;
import com.novi.carcompany.dtos.businessEntities.CarDto;
import com.novi.carcompany.dtos.businessEntities.CarInputDto;
import com.novi.carcompany.filters.JwtRequestFilter;
import com.novi.carcompany.services.businessEntities.CarService;
import com.novi.carcompany.services.security.CustomUserDetailsService;
import com.novi.carcompany.utilities.JwtUtility;
import org.junit.jupiter.api.BeforeEach;
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
@WebMvcTest(CarController.class)
@AutoConfigureMockMvc(addFilters = false)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtUtility jwtUtility;

    @MockBean
    private CarService carService;


    CarDto carDtoOne = new CarDto();
    CarDto carDtoTwo = new CarDto();
    CarInputDto carInputDtoOne = new CarInputDto();


    @BeforeEach
    void setUp() {

        carDtoOne.licensePlate = "NL-01-NL";
        carDtoOne.brand = "Test";
        carDtoOne.model = "Test1";
        carDtoOne.vinNumber = "1111111111";
        carDtoOne.color = "blue";
        carDtoOne.engine = "2.5";
        carDtoOne.winterTyres = false;
        /////
        carDtoTwo.licensePlate = "NL-02-NL";
        carDtoTwo.brand = "Test";
        carDtoTwo.model = "Test2";
        carDtoTwo.vinNumber = "2222222222";
        carDtoTwo.color = "red";
        carDtoTwo.engine = "2.0";
        carDtoTwo.winterTyres = true;
        /////
        carInputDtoOne.licensePlate = "NL-01-NL";
        carInputDtoOne.brand = "Test";
        carInputDtoOne.model = "Test1";
        carInputDtoOne.vinNumber = "1111111111";
        carInputDtoOne.color = "blue";
        carInputDtoOne.engine = "2.5";
        carInputDtoOne.winterTyres = false;
    }


    @Test
    void getCars() throws Exception {
        given(carService.getCars()).willReturn(List.of(carDtoOne, carDtoTwo));

        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].licensePlate").value("NL-01-NL"))
                .andExpect(jsonPath("$[0].brand").value("Test"))
                .andExpect(jsonPath("$[0].model").value("Test1"))
                .andExpect(jsonPath("$[0].vinNumber").value("1111111111"))
                .andExpect(jsonPath("$[0].color").value("blue"))
                .andExpect(jsonPath("$[0].engine").value("2.5"))
                .andExpect(jsonPath("$[0].winterTyres").value(false))
                //
                .andExpect(jsonPath("$[1].licensePlate").value("NL-02-NL"))
                .andExpect(jsonPath("$[1].brand").value("Test"))
                .andExpect(jsonPath("$[1].model").value("Test2"))
                .andExpect(jsonPath("$[1].vinNumber").value("2222222222"))
                .andExpect(jsonPath("$[1].color").value("red"))
                .andExpect(jsonPath("$[1].engine").value("2.0"))
                .andExpect(jsonPath("$[1].winterTyres").value(true));
    }

    @Test
    void getCar() throws Exception {
        given(carService.getCar("NL-01-NL")).willReturn(carDtoOne);

        mockMvc.perform(get("/cars/NL-01-NL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("licensePlate").value("NL-01-NL"))
                .andExpect(jsonPath("brand").value("Test"))
                .andExpect(jsonPath("model").value("Test1"))
                .andExpect(jsonPath("vinNumber").value("1111111111"))
                .andExpect(jsonPath("color").value("blue"))
                .andExpect(jsonPath("engine").value("2.5"))
                .andExpect(jsonPath("winterTyres").value(false));
    }

    @Test
    void findCarByVinNumber() throws Exception {
        given(carService.findCarByVinNumber("1111111111")).willReturn(Collections.singletonList((carDtoOne)));

        mockMvc.perform(get("/cars/findVin?vinNumber=1111111111"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].licensePlate").value("NL-01-NL"))
                .andExpect(jsonPath("$[0].brand").value("Test"))
                .andExpect(jsonPath("$[0].model").value("Test1"))
                .andExpect(jsonPath("$[0].vinNumber").value("1111111111"))
                .andExpect(jsonPath("$[0].color").value("blue"))
                .andExpect(jsonPath("$[0].engine").value("2.5"))
                .andExpect(jsonPath("$[0].winterTyres").value(false));
    }

    @Test
    void searchCar() throws Exception {
        given(carService.findCar("Test", "Test1")).willReturn(Collections.singletonList(carDtoOne));

        mockMvc.perform(get("/cars/find?brand=Test&model=Test1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].licensePlate").value("NL-01-NL"))
                .andExpect(jsonPath("$[0].brand").value("Test"))
                .andExpect(jsonPath("$[0].model").value("Test1"))
                .andExpect(jsonPath("$[0].vinNumber").value("1111111111"))
                .andExpect(jsonPath("$[0].color").value("blue"))
                .andExpect(jsonPath("$[0].engine").value("2.5"))
                .andExpect(jsonPath("$[0].winterTyres").value(false));
    }

    @Test
    void createCar() throws Exception {
        given(carService.createCar(any())).willReturn(carDtoOne);

        mockMvc.perform(post("/cars")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonInputDtoString(carInputDtoOne)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("licensePlate").value("NL-01-NL"))
                .andExpect(jsonPath("brand").value("Test"))
                .andExpect(jsonPath("model").value("Test1"))
                .andExpect(jsonPath("vinNumber").value("1111111111"))
                .andExpect(jsonPath("color").value("blue"))
                .andExpect(jsonPath("engine").value("2.5"))
                .andExpect(jsonPath("winterTyres").value(false));
    }

    @Test
    void changeCar() throws Exception {
        given(carService.changeCar("NL-01-NL", carDtoOne)).willReturn(carDtoOne);

        mockMvc.perform(put("/cars/NL-01-NL")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonDtoString(carDtoOne)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("licensePlate").value("NL-01-NL"))
                .andExpect(jsonPath("brand").value("Test"))
                .andExpect(jsonPath("model").value("Test1"))
                .andExpect(jsonPath("vinNumber").value("1111111111"))
                .andExpect(jsonPath("color").value("blue"))
                .andExpect(jsonPath("engine").value("2.5"))
                .andExpect(jsonPath("winterTyres").value(false));
    }

    @Test
    void deleteCar() throws Exception {
        mockMvc.perform(delete("/cars/NL-01-NL"))
                .andExpect(status().isOk());
    }


    private String asJsonInputDtoString(final CarInputDto obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String asJsonDtoString(final CarDto obj) {
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