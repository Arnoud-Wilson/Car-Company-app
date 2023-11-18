package com.novi.carcompany;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.novi.carcompany.dtos.businessEntities.*;
import com.novi.carcompany.models.businessEntities.Customer;
import com.novi.carcompany.repositories.CustomerRepository;
import com.novi.carcompany.repositories.UserRepository;
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
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class CustomerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;


    Customer customerOne = new Customer();
    Customer customerTwo = new Customer();
    Customer customerThree = new Customer();

    CustomerDto customerDto = new CustomerDto();

    CustomerInputDto customerInputDto = new CustomerInputDto();



    @BeforeEach
    void setUp() {
        customerOne.setSurName("Test");
        customerOne.setLastName("Tester one");
        customerOne.setAddress("Teststraat 1");
        customerOne.setPhoneNumber("0612345678");
        customerOne.setBankAccount("NL01TEST11111");
        customerOne.setCorporate(false);
        /////
        customerTwo.setSurName("Test");
        customerTwo.setLastName("Tester two");
        customerTwo.setAddress("Teststraat 2");
        customerTwo.setPhoneNumber("0687654321");
        customerTwo.setBankAccount("NL02TEST22222");
        customerTwo.setCorporate(true);
        /////
        customerThree.setSurName("Test");
        customerThree.setLastName("Tester three");
        customerThree.setAddress("Teststraat 3");
        customerThree.setPhoneNumber("0633333333");
        customerThree.setBankAccount("NL03TEST33333");
        customerThree.setCorporate(false);
        /////
        customerDto.surName = "Test";
        customerDto.lastName = "Tester one";
        customerDto.address = "Teststraat 1";
        customerDto.phoneNumber = "0612345678";
        customerDto.bankAccount = "NL01TEST11111";
        customerDto.corporate = false;
        /////
        customerInputDto.surName = "Test";
        customerInputDto.lastName = "Tester two";
        customerInputDto.address = "Teststraat 2";
        customerInputDto.phoneNumber = "0687654321";
        customerInputDto.bankAccount = "NL02TEST22222";
        customerInputDto.corporate = true;
        ////
        customerRepository.deleteAll();

        customerRepository.save(customerOne);
        customerRepository.save(customerTwo);
    }

    @AfterEach
    public void tearDown() {
        customerRepository.deleteAll();
    }


    @Test
    void getCustomers() throws Exception {
        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].surName").value("Test"))
                .andExpect(jsonPath("$[0].lastName").value("Tester one"))
                .andExpect(jsonPath("$[0].address").value("Teststraat 1"))
                .andExpect(jsonPath("$[0].phoneNumber").value("0612345678"))
                .andExpect(jsonPath("$[0].bankAccount").value("NL01TEST11111"))
                .andExpect(jsonPath("$[0].corporate").value(false))
                //
                .andExpect(jsonPath("$[1].surName").value("Test"))
                .andExpect(jsonPath("$[1].lastName").value("Tester two"))
                .andExpect(jsonPath("$[1].address").value("Teststraat 2"))
                .andExpect(jsonPath("$[1].phoneNumber").value("0687654321"))
                .andExpect(jsonPath("$[1].bankAccount").value("NL02TEST22222"))
                .andExpect(jsonPath("$[1].corporate").value(true));
    }

    @Test
    void getCustomer() throws Exception {
        mockMvc.perform(get("/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("surName").value("Test"))
                .andExpect(jsonPath("lastName").value("Tester one"))
                .andExpect(jsonPath("address").value("Teststraat 1"))
                .andExpect(jsonPath("phoneNumber").value("0612345678"))
                .andExpect(jsonPath("bankAccount").value("NL01TEST11111"))
                .andExpect(jsonPath("corporate").value(false));
    }

    @Test
    void getCustomerByName() throws Exception {
        mockMvc.perform(get("/customers/name?surname=Test&lastname=Tester one"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].surName").value("Test"))
                .andExpect(jsonPath("$[0].lastName").value("Tester one"))
                .andExpect(jsonPath("$[0].address").value("Teststraat 1"))
                .andExpect(jsonPath("$[0].phoneNumber").value("0612345678"))
                .andExpect(jsonPath("$[0].bankAccount").value("NL01TEST11111"))
                .andExpect(jsonPath("$[0].corporate").value(false));
    }

    @Test
    void getCustomersByCorporate() throws Exception {
        mockMvc.perform(get("/customers/corporate?corporate=true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].surName").value("Test"))
                .andExpect(jsonPath("$[0].lastName").value("Tester two"))
                .andExpect(jsonPath("$[0].address").value("Teststraat 2"))
                .andExpect(jsonPath("$[0].phoneNumber").value("0687654321"))
                .andExpect(jsonPath("$[0].bankAccount").value("NL02TEST22222"))
                .andExpect(jsonPath("$[0].corporate").value(true));
    }

    @Test
    void createCustomer() throws Exception {
        customerRepository.deleteAll();
        mockMvc.perform(post("/customers")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonInputDtoString(customerInputDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("surName").value("Test"))
                .andExpect(jsonPath("lastName").value("Tester two"))
                .andExpect(jsonPath("address").value("Teststraat 2"))
                .andExpect(jsonPath("phoneNumber").value("0687654321"))
                .andExpect(jsonPath("bankAccount").value("NL02TEST22222"))
                .andExpect(jsonPath("corporate").value(true));
    }

    @Test
    void deleteCustomer() throws Exception {
        mockMvc.perform(delete("/customers/3"))
                .andExpect(status().isOk());
    }

    private String asJsonDtoString(final CustomerDto obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String asJsonInputDtoString(final CustomerInputDto obj) {
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
