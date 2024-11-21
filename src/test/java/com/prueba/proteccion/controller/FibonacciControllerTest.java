package com.prueba.proteccion.controller;

import com.prueba.proteccion.controllers.FibonacciController;
import com.prueba.proteccion.services.FibonacciService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(FibonacciController.class)
public class FibonacciControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String URL = "/fibonacci";

    @Test
    public void testCalcularFibonacci() throws Exception {

        // POST
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"formatoHora\": \"12:23:04\"}"))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertEquals("Estado de respuesta incorrecto", HttpStatus.CREATED.value(), status);

        String responseContent = result.getResponse().getContentAsString();
        assertNotNull(responseContent);

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"formatoHora\": \"12:23:04\"}"))
//                .andDo(print())
//                .andExpect(jsonPath("$.formatoHora").value("12:23:04"))
//                .andExpect(jsonPath("$.serie").value("[21, 13, 8, 5, 3, 2]")) // Aquí comparamos la serie como un String
//                .andExpect(jsonPath("$.serie.length()").value(6))
                ;
    }

}
