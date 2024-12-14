package com.prueba.proteccion.integration;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.proteccion.entities.FibonacciEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
public class FibonacciApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void test_Create_And_FindEntity() throws Exception {
        List<String> listTimes = new ArrayList<>(List.of("12:23:04", "18:36:11", "05:12:43"));

        FibonacciEntity fibonacciInput = new FibonacciEntity();

        for (String time: listTimes) {
            fibonacciInput.setFormatoHora(time);
            String jsonRequest = objectMapper.writeValueAsString(fibonacciInput);
            ResultActions response =  mockMvc.perform(post("/fibonacci")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest));

            response.andExpect(status().isCreated());
        }

        MvcResult response =  mockMvc.perform(get("/fibonacci")).andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        List<FibonacciEntity> items = objectMapper.readValue(
                response.getResponse().getContentAsString(),
                new TypeReference<>() {}
        );

        for (FibonacciEntity item: items) {
            System.out.println(item.getSerie());
        }

        assertThat(items).hasSize(3);

        assertThat(items)
                .extracting(FibonacciEntity::getFormatoHora)
                .containsExactly("12:23:04", "18:36:11", "05:12:43");

        assertThat(items)
                .extracting(FibonacciEntity::getSerie)
                .containsExactly(
                        "[21, 13, 8, 5, 3, 2]",
                        "[1131, 699, 432, 267, 165, 102, 63, 39, 24, 15, 9, 6, 3]",
                        "[1836311903, 1134903170, 701408733, 433494437, 267914296, 165580141, 102334155, 63245986, 39088169, 24157817, 14930352, 9227465, 5702887, 3524578, 2178309, 1346269, 832040, 514229, 317811, 196418, 121393, 75025, 46368, 28657, 17711, 10946, 6765, 4181, 2584, 1597, 987, 610, 377, 233, 144, 89, 55, 34, 21, 13, 8, 5, 3, 2, 1]"
                );
    }
}
