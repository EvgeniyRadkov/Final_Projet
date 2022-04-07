package com.gmail.radzkovevgeni.legal.controllers;

import com.gmail.radzkovevgeni.legal.controllers.validator.CompanyValidator;
import com.gmail.radzkovevgeni.legal.service.CompanyService;
import com.gmail.radzkovevgeni.legal.service.model.CompanyDTO;
import com.gmail.radzkovevgeni.legal.service.model.ViewCompanyDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/legals")
public class CompanyController {
    private static final String MESSAGE_WHEN_PARAMETERS_ARE_NOT_VALID = "неверно заданы параметры";
    private static final String MESSAGE_IF_COMPANY_EXISTS = "Компания существует с параметрами ";
    private static final String MESSAGE_IF_COMPANY_IS_CREATED = "Компания успешно создана";
    private static final String MESSAGE_IF_COMPANY_DOES_NOT_EXIST = "Компания не существует";
    private CompanyService companyService;
    private CompanyValidator companyValidator;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Optional<ViewCompanyDTO> addCompany(@Validated @RequestBody CompanyDTO companyDTO,
                                               BindingResult bindingResult,
                                               HttpServletResponse response) throws IOException {
        if (bindingResult.hasErrors()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    MESSAGE_WHEN_PARAMETERS_ARE_NOT_VALID);
            return Optional.empty();
        }
        companyValidator.validate(companyDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            response.sendError(HttpServletResponse.SC_CONFLICT,
                    MESSAGE_IF_COMPANY_EXISTS + bindingResult.getNestedPath()
            );
            return Optional.empty();
        }
        Optional<ViewCompanyDTO> optionalViewCompanyDTO = companyService.add(companyDTO);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        if (optionalViewCompanyDTO.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return Optional.empty();
        } else {
            response.sendError(HttpServletResponse.SC_CREATED,
                    MESSAGE_IF_COMPANY_IS_CREATED);
            return optionalViewCompanyDTO;
        }
    }

    @GetMapping(params = {"LegalId"})
    public Optional<ViewCompanyDTO> findCompanyById(@RequestParam(name = "LegalId") Long legalId,
                                                    HttpServletResponse response) throws IOException {
        Optional<ViewCompanyDTO> viewCompanyDTO = companyService.getById(legalId);
        if (viewCompanyDTO.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    MESSAGE_IF_COMPANY_DOES_NOT_EXIST);
            return Optional.empty();
        } else {
            return viewCompanyDTO;
        }
    }

    @GetMapping(params = {"companyName"})
    public Optional<ViewCompanyDTO> getCompanyByName(@RequestParam("companyName") String companyName) {
        Optional<ViewCompanyDTO> viewCompanyDTO = companyService.getByName(companyName);
        if (viewCompanyDTO.isEmpty()) {
            return Optional.empty();
        } else {
            return viewCompanyDTO;
        }
    }
}
