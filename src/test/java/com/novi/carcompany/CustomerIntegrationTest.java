package com.novi.carcompany;

import com.novi.carcompany.dtos.businessEntities.*;
import com.novi.carcompany.models.businessEntities.Customer;
import com.novi.carcompany.repositories.CustomerRepository;
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


    Customer customerOne = new Customer();
    Customer customerTwo = new Customer();

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
        /////
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
        mockMvc.perform(get("/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].surName").value("Test"))
                .andExpect(jsonPath("$[0].lastName").value("Tester one"))
                .andExpect(jsonPath("$[0].address").value("Teststraat 1"))
                .andExpect(jsonPath("$[0].phoneNumber").value("0612345678"))
                .andExpect(jsonPath("$[0].bankAccount").value("NL01TEST11111"))
                .andExpect(jsonPath("$[0].corporate").value(false));
    }
//
//    @Test
//    void getPartsByName() throws Exception {
//        mockMvc.perform(get("/parts/findName?name=Test one"))
//                .andExpect(status().isOk())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].partNumber").value("11111"))
//                .andExpect(jsonPath("$[0].name").value("Test one"))
//                .andExpect(jsonPath("$[0].description").value("Test onderdeel"))
//                .andExpect(jsonPath("$[0].location").value("A1-01"))
//                .andExpect(jsonPath("$[0].stock").value(2))
//                .andExpect(jsonPath("$[0].purchasePrice").value(10.25))
//                .andExpect(jsonPath("$[0].sellingPrice").value(15.35));
//    }
//
//    @Test
//    void getPartsOnStock() throws Exception {
//        mockMvc.perform(get("/parts/onStock"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].partNumber").value("11111"))
//                .andExpect(jsonPath("$[0].name").value("Test one"))
//                .andExpect(jsonPath("$[0].description").value("Test onderdeel"))
//                .andExpect(jsonPath("$[0].location").value("A1-01"))
//                .andExpect(jsonPath("$[0].stock").value(2))
//                .andExpect(jsonPath("$[0].purchasePrice").value(10.25))
//                .andExpect(jsonPath("$[0].sellingPrice").value(15.35))
//                //
//                .andExpect(jsonPath("$[1].partNumber").value("22222"))
//                .andExpect(jsonPath("$[1].name").value("Test two"))
//                .andExpect(jsonPath("$[1].description").value("Test onderdeel"))
//                .andExpect(jsonPath("$[1].location").value("A2-02"))
//                .andExpect(jsonPath("$[1].stock").value(5))
//                .andExpect(jsonPath("$[1].purchasePrice").value(5.0))
//                .andExpect(jsonPath("$[1].sellingPrice").value(10.50));
//    }
//
//    @Test
//    void createPart() throws Exception {
//        partRepository.deleteAll();
//        mockMvc.perform(post("/parts")
//                        .contentType(APPLICATION_JSON)
//                        .content(asJsonInputDtoString(partInputDto)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("partNumber").value("11111"))
//                .andExpect(jsonPath("name").value("Test one"))
//                .andExpect(jsonPath("description").value("Test onderdeel"))
//                .andExpect(jsonPath("location").value("A1-01"))
//                .andExpect(jsonPath("stock").value(2))
//                .andExpect(jsonPath("purchasePrice").value(10.25))
//                .andExpect(jsonPath("sellingPrice").value(15.35));
//    }
//
//    @Test
//    void changePart() throws Exception {
//        mockMvc.perform(put("/parts/11111")
//                        .contentType(APPLICATION_JSON)
//                        .content(asJsonChangeInputDtoString(partChangeInputDto)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("partNumber").value("11111"))
//                .andExpect(jsonPath("name").value("Test one"))
//                .andExpect(jsonPath("description").value("Test onderdeel"))
//                .andExpect(jsonPath("location").value("A1-01"))
//                .andExpect(jsonPath("stock").value(4))
//                .andExpect(jsonPath("purchasePrice").value(10.25))
//                .andExpect(jsonPath("sellingPrice").value(15.35));
//    }
//
//    @Test
//    void deletePart() throws Exception {
//        mockMvc.perform(delete("/parts/11111"))
//                .andExpect(status().isOk());
//    }
//
//
//    private String asJsonInputDtoString(final PartInputDto obj) {
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.registerModule(new JavaTimeModule());
//            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//            return mapper.writeValueAsString(obj);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private String asJsonChangeInputDtoString(final PartChangeInputDto obj) {
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.registerModule(new JavaTimeModule());
//            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//            return mapper.writeValueAsString(obj);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
