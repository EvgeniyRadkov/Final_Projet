package com.gmail.vanyasudnishnikov.application.service;

import com.gmail.vanyasudnishnikov.application.service.model.ViewCompanyDTO;

import java.util.Optional;

public interface CompanyService {
    Optional<ViewCompanyDTO> getByName(String nameLegal);

    Boolean attachApplication(String nameLegal, Long applicationId);
}
