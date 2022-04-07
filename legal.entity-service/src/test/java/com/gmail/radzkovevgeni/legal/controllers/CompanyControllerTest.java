package com.gmail.radzkovevgeni.legal.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.radzkovevgeni.legal.controllers.filters.JwtTokenFilter;
import com.gmail.radzkovevgeni.legal.controllers.validator.CompanyValidator;
import com.gmail.radzkovevgeni.legal.service.CompanyService;
import com.gmail.radzkovevgeni.legal.service.model.CompanyDTO;
import com.gmail.radzkovevgeni.legal.service.model.LegalEnum;
import com.gmail.radzkovevgeni.legal.service.model.ViewCompanyDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CompanyController.class)
@AutoConfigureMockMvc(addFilters = false)
class CompanyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext context;
    @MockBean
    private CompanyService companyService;
    @MockBean
    private CompanyValidator companyValidator;
    @MockBean
    private JwtTokenFilter jwtTokenFilter;

    @Test
    void shouldReturns200whenInValidInput() throws Exception {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setNameLegal("Test");
        companyDTO.setUnp(343242342);
        companyDTO.setIbanByBYN("BY34UNBS32432432432432442312");
        companyDTO.setTotalEmployees(423);
        companyDTO.setTypeLegal(LegalEnum.Resident);
        ViewCompanyDTO companyDetails = ViewCompanyDTO.builder()
                .nameLegal("test")
                .unp(343242342)
                .ibanByBYN("BY34UNBS32432432432432442312")
                .legalId(1L)
                .typeLegal("type")
                .totalEmployees(1123)
                .build();
        when(companyService.add(companyDTO)).thenReturn(Optional.ofNullable(companyDetails));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/legals")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(companyDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn415WhenWePostCompany() throws Exception {
        mockMvc.perform(post("/api/legals")
                        .contentType(MediaType.APPLICATION_ATOM_XML_VALUE))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    void shouldReturn404WhenUrlInValidForPost() throws Exception {
        mockMvc.perform(post("/api/legal")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn400WhenAddedUsernameIsBlank() throws Exception {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setNameLegal(" ");
        companyDTO.setUnp(343242342);
        companyDTO.setIbanByBYN("BY34UNBS32432432432432442312");
        companyDTO.setTotalEmployees(423);
        companyDTO.setTypeLegal(LegalEnum.Resident);
        ViewCompanyDTO companyDetails = ViewCompanyDTO.builder()
                .nameLegal("test")
                .unp(343242342)
                .ibanByBYN("BY34UNBS32432432432432442312")
                .legalId(1L)
                .typeLegal("type")
                .totalEmployees(1123)
                .build();
        when(companyService.add(companyDTO)).thenReturn(Optional.ofNullable(companyDetails));
        MvcResult mvcResult = mockMvc.perform(post("/api/legals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(companyDTO)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void shouldReturnErrorWhenAddedUnpIsNull() throws Exception {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setNameLegal("Test");
        companyDTO.setUnp(null);
        companyDTO.setIbanByBYN("BY34UNBS32432432432432442312");
        companyDTO.setTotalEmployees(423);
        companyDTO.setTypeLegal(LegalEnum.Resident);

        MvcResult mvcResult = mockMvc.perform(post("/api/legals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(companyDTO)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}