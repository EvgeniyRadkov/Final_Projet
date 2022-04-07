package com.gmail.radzkovevgeni.legal.service;

import com.gmail.radzkovevgeni.legal.service.model.CompanyDTO;
import com.gmail.radzkovevgeni.legal.service.model.ViewCompanyDTO;

import java.util.Optional;

public interface CompanyService {
    Optional<ViewCompanyDTO> add(CompanyDTO companyDTO);

    Optional<ViewCompanyDTO> getById(Long legalId);

    Optional<ViewCompanyDTO> getByName(String companyName);
}
