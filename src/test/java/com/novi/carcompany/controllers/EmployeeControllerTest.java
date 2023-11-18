package com.novi.carcompany.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.novi.carcompany.controllers.businessEntities.EmployeeController;
import com.novi.carcompany.dtos.businessEntities.EmployeeDto;
import com.novi.carcompany.dtos.businessEntities.EmployeeInputDto;
import com.novi.carcompany.filters.JwtRequestFilter;
import com.novi.carcompany.services.businessEntities.EmployeeService;
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
@WebMvcTest(EmployeeController.class)
@AutoConfigureMockMvc(addFilters = false)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtUtility jwtUtility;

    @MockBean
    private EmployeeService employeeService;


    EmployeeDto employeeDtoOne = new EmployeeDto();
    EmployeeDto employeeDtoTwo = new EmployeeDto();

    EmployeeInputDto employeeInputDtoOne = new EmployeeInputDto();


    @BeforeEach
    void setUp() {
        employeeDtoOne.id = 1L;
        employeeDtoOne.surName = "Test";
        employeeDtoOne.lastName = "One";
        employeeDtoOne.address = "Teststraat 1";
        employeeDtoOne.phoneNumber = "1111111111";
        employeeDtoOne.function = "Tester";
        /////
        employeeDtoTwo.id = 2L;
        employeeDtoTwo.surName = "Test";
        employeeDtoTwo.lastName = "Two";
        employeeDtoTwo.address = "Teststraat 2";
        employeeDtoTwo.phoneNumber = "2222222222";
        employeeDtoTwo.function = "Tester";
        /////
        employeeInputDtoOne.surName = "Test";
        employeeInputDtoOne.lastName = "One";
        employeeInputDtoOne.address = "Teststraat 1";
        employeeInputDtoOne.phoneNumber = "1111111111";
        employeeInputDtoOne.function = "Tester";
    }


    @Test
    void getEmployees() throws Exception {
        given(employeeService.getAllEmployees()).willReturn(List.of(employeeDtoOne, employeeDtoTwo));

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].surName").value("Test"))
                .andExpect(jsonPath("$[0].lastName").value("One"))
                .andExpect(jsonPath("$[0].address").value("Teststraat 1"))
                .andExpect(jsonPath("$[0].phoneNumber").value("1111111111"))
                .andExpect(jsonPath("$[0].function").value("Tester"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].surName").value("Test"))
                .andExpect(jsonPath("$[1].lastName").value("Two"))
                .andExpect(jsonPath("$[1].address").value("Teststraat 2"))
                .andExpect(jsonPath("$[1].phoneNumber").value("2222222222"))
                .andExpect(jsonPath("$[1].function").value("Tester"));
    }

    @Test
    void getEmployeeById() throws Exception {
        given(employeeService.getEmployeeById(1L)).willReturn(employeeDtoOne);

        mockMvc.perform(get("/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("surName").value("Test"))
                .andExpect(jsonPath("lastName").value("One"))
                .andExpect(jsonPath("address").value("Teststraat 1"))
                .andExpect(jsonPath("phoneNumber").value("1111111111"))
                .andExpect(jsonPath("function").value("Tester"));
    }

    @Test
    void getEmployeeByName() throws Exception {
        given(employeeService.getEmployeeByName("Test", "One")).willReturn(Collections.singletonList(employeeDtoOne));

        mockMvc.perform(get("/employees/name?surname=Test&lastname=One"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].surName").value("Test"))
                .andExpect(jsonPath("$[0].lastName").value("One"))
                .andExpect(jsonPath("$[0].address").value("Teststraat 1"))
                .andExpect(jsonPath("$[0].phoneNumber").value("1111111111"))
                .andExpect(jsonPath("$[0].function").value("Tester"));
    }

    @Test
    void getEmployeeByFunction() throws Exception {
        given(employeeService.getEmployeeByFunction("Tester")).willReturn(Collections.singletonList(employeeDtoOne));

        mockMvc.perform(get("/employees/function?function=Tester"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].surName").value("Test"))
                .andExpect(jsonPath("$[0].lastName").value("One"))
                .andExpect(jsonPath("$[0].address").value("Teststraat 1"))
                .andExpect(jsonPath("$[0].phoneNumber").value("1111111111"))
                .andExpect(jsonPath("$[0].function").value("Tester"));
    }

    @Test
    void createEmployee() throws Exception {
        given(employeeService.createEmployee(any())).willReturn(employeeDtoOne);

        mockMvc.perform(post("/employees")
                    .contentType(APPLICATION_JSON)
                    .content(asJsonInputDtoString(employeeInputDtoOne)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("surName").value("Test"))
                .andExpect(jsonPath("lastName").value("One"))
                .andExpect(jsonPath("address").value("Teststraat 1"))
                .andExpect(jsonPath("phoneNumber").value("1111111111"))
                .andExpect(jsonPath("function").value("Tester"));
    }

    @Test
    void changeEmployee() throws Exception {
        given(employeeService.changeEmployee(1L, employeeDtoOne)).willReturn(employeeDtoOne);

        mockMvc.perform(put("/employees/1")
                    .contentType(APPLICATION_JSON)
                    .content(asJsonDtoString(employeeDtoOne)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("surName").value("Test"))
                .andExpect(jsonPath("lastName").value("One"))
                .andExpect(jsonPath("address").value("Teststraat 1"))
                .andExpect(jsonPath("phoneNumber").value("1111111111"))
                .andExpect(jsonPath("function").value("Tester"));
    }

    @Test
    void deleteEmployee() throws Exception {
        given(employeeService.deleteEmployee(1L)).willReturn(employeeDtoOne);

        mockMvc.perform(delete("/employees/1"))
                .andExpect(status().isOk());
    }


    private String asJsonInputDtoString(final EmployeeInputDto obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String asJsonDtoString(final EmployeeDto obj) {
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