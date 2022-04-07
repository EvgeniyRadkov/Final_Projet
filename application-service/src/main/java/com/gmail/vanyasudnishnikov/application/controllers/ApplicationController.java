package com.gmail.vanyasudnishnikov.application.controllers;

import com.gmail.vanyasudnishnikov.application.controllers.validation.ValuesAllowed;
import com.gmail.vanyasudnishnikov.application.service.ApplicationService;
import com.gmail.vanyasudnishnikov.application.service.CompanyService;
import com.gmail.vanyasudnishnikov.application.service.StatusService;
import com.gmail.vanyasudnishnikov.application.service.model.ViewApplicationDTO;
import com.gmail.vanyasudnishnikov.application.service.model.ViewCompanyDTO;
import com.gmail.vanyasudnishnikov.application.service.model.ViewStatusDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Validated
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(value = "/api/applications")
public class ApplicationController {
    private ApplicationService applicationService;
    private StatusService statusService;
    private CompanyService companyService;

    @GetMapping
    @RequestMapping(value = "/{id}")
    public ViewApplicationDTO getById(@PathVariable Long id,
                                      HttpServletResponse response) {
        Optional<ViewApplicationDTO> viewApplicationDTO = applicationService.getById(id);
        if (viewApplicationDTO.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        } else {
            return viewApplicationDTO.get();
        }
    }

    @PutMapping
    public ViewStatusDTO changeStatus(@ValuesAllowed(propName = "status", values = {"New", "In progress", "Done", "Rejected"})
                                      @RequestParam(name = "status") String status,
                                      @RequestParam(name = "applicationConvId") Long applicationId,
                                      HttpServletResponse response) {
        try {
            Optional<ViewStatusDTO> viewStatusDTO = statusService.changeInApplication(applicationId, status);
            if (viewStatusDTO.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                return null;
            } else {
                return viewStatusDTO.get();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
    }

    @PutMapping(params = {"Name_Legal"})
    @RequestMapping(value = "/{id}", params = {"Name_Legal"})
    public Long attachToCompany(@PathVariable Long id,
                                @RequestParam(name = "Name_Legal") String nameLegal,
                                HttpServletResponse response) throws IOException {
        Optional<ViewCompanyDTO> optionalViewCompanyDTO = companyService.getByName(nameLegal);
        if (optionalViewCompanyDTO.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Кампания с " + nameLegal + " не существует");
            return null;
        }
        Optional<ViewApplicationDTO> optionalViewApplicationDTO = applicationService.getById(id);
        if (optionalViewApplicationDTO.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Заявка с " + id + " не существует");
            return null;
        }
        Long legalId = optionalViewCompanyDTO.get().getLegalId();
        String nameCompany = optionalViewApplicationDTO.get().getNameLegal();
        if (!nameCompany.equals(nameLegal)) {
            Boolean attachApplication = companyService.attachApplication(nameLegal, id);
            if (!attachApplication) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return null;
            }
        }
        return legalId;
    }
}
