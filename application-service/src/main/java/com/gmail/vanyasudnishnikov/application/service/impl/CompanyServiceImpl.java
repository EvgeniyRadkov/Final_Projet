package com.gmail.vanyasudnishnikov.application.service.impl;

import com.gmail.vanyasudnishnikov.application.repository.ApplicationRepository;
import com.gmail.vanyasudnishnikov.application.repository.model.Application;
import com.gmail.vanyasudnishnikov.application.service.CompanyService;
import com.gmail.vanyasudnishnikov.application.repository.feign.CompanyFeign;
import com.gmail.vanyasudnishnikov.application.service.model.ViewCompanyDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService {
    private static final String APPLICATION_NOT_FOUND = "Application not found.";
    private CompanyFeign companyFeign;
    private ApplicationRepository applicationRepository;

    @Override
    @Transactional
    public Optional<ViewCompanyDTO> getByName(String nameLegal) {
        try {
            return companyFeign.getCompanyByName(nameLegal);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Boolean attachApplication(String nameLegal, Long applicationId) {
        Optional<Application> optionalApplication = applicationRepository.findById(applicationId);
        if (optionalApplication.isEmpty()) {
            throw new IllegalArgumentException(APPLICATION_NOT_FOUND);
        }
        Application application = optionalApplication.get();
        application.setNameLegal(nameLegal);
        return applicationRepository.update(application);
    }
}
