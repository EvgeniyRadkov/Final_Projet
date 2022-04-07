package com.gmail.vanyasudnishnikov.application.controllers;

import com.gmail.vanyasudnishnikov.application.service.ApplicationService;
import com.gmail.vanyasudnishnikov.application.service.ApplicationsService;
import com.gmail.vanyasudnishnikov.application.service.model.ApplicationDTO;
import com.gmail.vanyasudnishnikov.application.service.FileService;
import com.gmail.vanyasudnishnikov.application.service.model.PaginationDTO;
import com.gmail.vanyasudnishnikov.application.service.model.ViewApplicationDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api")
public class ApplicationsController {
    private FileService fileService;
    private ApplicationsService applicationsService;
    private ApplicationService applicationService;


    @PostMapping
    @RequestMapping(value = "/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity addApplicationFromFile(@RequestParam("file") MultipartFile file) {
        List<ApplicationDTO> applications = fileService.createApplicationFromFile(file);
        if (applications.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Boolean unique = applicationsService.isUnique(applications);
        if (!unique) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Boolean added = applicationsService.add(applications);
        if (!added) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(params = {"pagination", "page"})
    @RequestMapping(value = "/applications")
    public List<ViewApplicationDTO> getAll(@Validated @ModelAttribute PaginationDTO paginationDTO,
                                           @RequestParam("customized_page") String customizedPage,
                                           @PageableDefault(sort = {"legalId"}, size = 10,
                                                   direction = Sort.Direction.DESC) Pageable pageable,
                                           HttpServletResponse response) {
        List<ViewApplicationDTO> viewApplications = applicationService.getAll(paginationDTO, pageable, customizedPage);
        if (viewApplications.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return Collections.emptyList();
        } else {
            return viewApplications;
        }
    }
}
