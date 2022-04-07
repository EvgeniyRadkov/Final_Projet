package com.gmail.vanyasudnishnikov.authorization.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.vanyasudnishnikov.authorization.controllers.errors.AuthEntryPointJwt;
import com.gmail.vanyasudnishnikov.authorization.repository.EmployeeRepository;
import com.gmail.vanyasudnishnikov.authorization.service.*;
import com.gmail.vanyasudnishnikov.authorization.service.model.EmployeeDTO;
import com.gmail.vanyasudnishnikov.authorization.service.model.ViewEmployeeDTO;
import com.gmail.vanyasudnishnikov.authorization.service.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;


import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = EmployeeController.class)
@ContextConfiguration(classes = EmployeeController.class)
@AutoConfigureMockMvc(addFilters = false)
class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext context;
    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private EmployeeService employeeService;
    @MockBean
    private AuthEntryPointJwt authEntryPointJwt;
    @MockBean
    private EmployeeRepository employeeRepository;


    @Test
    void shouldReturn200WhenValidInput() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setUsername("evgeniy");
        employeeDTO.setPassword("passwordd");
        employeeDTO.setUsermail("fegr@example.com");
        employeeDTO.setFirstName("Евгений");

        ViewEmployeeDTO viewEmployeeDTO = new ViewEmployeeDTO();
        viewEmployeeDTO.setUserId(1L);
        viewEmployeeDTO.setStatus("ACTIVE");
        when(employeeService.add(employeeDTO)).thenReturn(Optional.of(viewEmployeeDTO));
        mockMvc.perform(post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn404WhenInvalidUrlForPost() throws Exception {
        mockMvc.perform(post("/api/auth/sign")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }
}