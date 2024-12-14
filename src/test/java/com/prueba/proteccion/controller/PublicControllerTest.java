package com.prueba.proteccion.controller;

import com.prueba.proteccion.config.JwtAuthFilter;
import com.prueba.proteccion.config.SecurityConfig;
import com.prueba.proteccion.config.SwaggerConfig;
import com.prueba.proteccion.controllers.PublicController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
    controllers = PublicController.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = {
            SecurityConfig.class,
            JwtAuthFilter.class,
            SwaggerConfig.class
        }))
@AutoConfigureMockMvc(addFilters = false)
public class PublicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetInfo() throws Exception {
        mockMvc.perform(get("/public/info"))
                .andExpect(status().isOk())
                .andExpect(content().string("This endpoint is public!"))
        ;
    }
}
