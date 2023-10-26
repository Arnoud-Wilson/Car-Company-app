package com.novi.carcompany.integrationTests;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.novi.carcompany.dtos.CarDto;
import com.novi.carcompany.dtos.CarInputDto;
import com.novi.carcompany.models.Car;
import com.novi.carcompany.repositories.CarRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
public class CarIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarRepository carRepository;


    Car carOne = new Car();
    Car carTwo = new Car();

    CarDto carDtoOne = new CarDto();

    CarInputDto carInputDto = new CarInputDto();


    @BeforeEach
    public void setUp() {
        carOne.setLicensePlate("NL-01-NL");
        carOne.setBrand("Test");
        carOne.setModel("Test1");
        carOne.setVinNumber("1111111111");
        carOne.setColor("blue");
        carOne.setEngine("2.5");
        carOne.setWinterTyres(false);

        carTwo.setLicensePlate("NL-02-NL");
        carTwo.setBrand("Test");
        carTwo.setModel("Test2");
        carTwo.setVinNumber("2222222222");
        carTwo.setColor("red");
        carTwo.setEngine("2.0");
        carTwo.setWinterTyres(true);
        /////
        carInputDto.licensePlate = "NL-01-NL";
        carInputDto.brand = "Test";
        carInputDto.model = "Test1";
        carInputDto.vinNumber = "1111111111";
        carInputDto.color = "blue";
        carInputDto.engine = "2.5";
        carInputDto.winterTyres = false;
        /////
        carDtoOne.licensePlate = "NL-01-NL";
        carDtoOne.brand = "Test";
        carDtoOne.model = "Test1";
        carDtoOne.vinNumber = "1111111111";
        carDtoOne.color = "blue";
        carDtoOne.engine = "2.5";
        carDtoOne.winterTyres = false;


        carRepository.save(carOne);
        carRepository.save(carTwo);
    }

    @AfterEach
    public void tearDown() {
        carRepository.deleteAll();
    }


    @Test
    @DisplayName("Invoke get all cars")
    void getCars() throws Exception {
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
        carRepository.deleteAll();
        mockMvc.perform(post("/cars")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonInputDtoString(carInputDto)))
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
