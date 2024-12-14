package com.prueba.proteccion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.proteccion.entities.FibonacciEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
public class FibonacciControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

   @Test
   public void test_FibonacciController_Created() throws Exception {
       FibonacciEntity fibonacciInput = new FibonacciEntity();
       fibonacciInput.setFormatoHora("12:23:04");

//       when(fibonacciService.guardarFibonacci(any(FibonacciEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

      String jsonRequest = objectMapper.writeValueAsString(fibonacciInput);

      ResultActions response =  mockMvc.perform(post("/fibonacci")
              .contentType(MediaType.APPLICATION_JSON)
              .content(jsonRequest));

      response.andExpect(status().isCreated())
              .andExpect(jsonPath("$.formatoHora").value("12:23:04"))
              .andExpect(jsonPath("$.serie").isString())
              .andExpect(jsonPath("$.serie").value("[21, 13, 8, 5, 3, 2]"));

    }

}
