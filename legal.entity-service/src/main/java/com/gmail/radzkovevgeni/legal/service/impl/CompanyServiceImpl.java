package com.gmail.radzkovevgeni.legal.service.impl;

import com.gmail.radzkovevgeni.legal.repository.CompanyDetailsRepository;
import com.gmail.radzkovevgeni.legal.repository.CompanyRepository;
import com.gmail.radzkovevgeni.legal.repository.model.Company;
import com.gmail.radzkovevgeni.legal.repository.model.CompanyDetails;
import com.gmail.radzkovevgeni.legal.repository.model.TypeLegalEnum;
import com.gmail.radzkovevgeni.legal.service.CompanyService;
import com.gmail.radzkovevgeni.legal.service.model.CompanyDTO;
import com.gmail.radzkovevgeni.legal.service.model.LegalEnum;
import com.gmail.radzkovevgeni.legal.service.model.ViewCompanyDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private static final String PATTERN_FOR_DATE = "dd/MM/yyyy";
    private CompanyRepository companyRepository;
    private CompanyDetailsRepository companyDetailsRepository;

    @Override
    @Transactional
    public Optional<ViewCompanyDTO> add(CompanyDTO companyDTO) {
        Company company = createCompany(companyDTO);
        Optional<Company> optionalCompany = companyRepository.add(company);
        if (optionalCompany.isEmpty()) {
            return Optional.empty();
        }
        Company addedCompany = optionalCompany.get();
        CompanyDetails companyDetails = createCompanyDetails(companyDTO);
        companyDetails.setCompany(addedCompany);
        Optional<CompanyDetails> optionalCompanyDetails = companyDetailsRepository.add(companyDetails);
        if (optionalCompanyDetails.isEmpty()) {
            return Optional.empty();
        }
        CompanyDetails addedCompanyDetails = optionalCompanyDetails.get();
        ViewCompanyDTO viewCompanyDTO = createViewCompanyDTO(addedCompany, addedCompanyDetails);
        return Optional.ofNullable(viewCompanyDTO);
    }

    @Override
    @Transactional
    public Optional<ViewCompanyDTO> getById(Long legalId) {
        Optional<Company> optionalCompany = companyRepository.findById(legalId);
        if (optionalCompany.isEmpty()) {
            return Optional.empty();
        }
        Company company = optionalCompany.get();
        CompanyDetails companyDetails = company.getCompanyDetails();
        ViewCompanyDTO viewCompanyDTO = createViewCompanyDTO(company, companyDetails);
        return Optional.ofNullable(viewCompanyDTO);
    }

    @Override
    @Transactional
    public Optional<ViewCompanyDTO> getByName(String companyName) {
        Optional<Company> optionalCompany = companyRepository.findByName(companyName);
        if (optionalCompany.isEmpty()) {
            return Optional.empty();
        }
        Company company = optionalCompany.get();
        CompanyDetails companyDetails = company.getCompanyDetails();
        ViewCompanyDTO viewCompanyDTO = createViewCompanyDTO(company, companyDetails);
        return Optional.ofNullable(viewCompanyDTO);
    }

    private ViewCompanyDTO createViewCompanyDTO(Company addedCompany, CompanyDetails addedCompanyDetails) {
        return ViewCompanyDTO.builder()
                .legalId(addedCompany.getId())
                .nameLegal(addedCompany.getName())
                .unp(Integer.valueOf(addedCompany.getUnp()))
                .ibanByBYN(addedCompany.getIbanByBYN())
                .typeLegal(String.valueOf(addedCompany.getTypeLegal()))
                .totalEmployees(addedCompanyDetails.getTotalEmployees())
                .build();
    }

    private CompanyDetails createCompanyDetails(CompanyDTO companyDTO) {
        CompanyDetails companyDetails = new CompanyDetails();
        Integer totalEmployees = companyDTO.getTotalEmployees();
        companyDetails.setTotalEmployees(totalEmployees);
        LocalDateTime nowDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FOR_DATE);
        String createDate = nowDate.format(formatter);
        companyDetails.setCreateDate(createDate);
        companyDetails.setUpdateDate("-");
        companyDetails.setNote("-");
        return companyDetails;
    }

    private Company createCompany(CompanyDTO companyDTO) {
        Company company = new Company();
        String nameCompany = companyDTO.getNameLegal();
        company.setName(nameCompany);
        Integer unp = companyDTO.getUnp();
        company.setUnp(String.valueOf(unp));
        String ibanByBYN = companyDTO.getIbanByBYN();
        company.setIbanByBYN(ibanByBYN);
        LegalEnum typeLegal = companyDTO.getTypeLegal();
        if (typeLegal == LegalEnum.Resident) {
            company.setTypeLegal(TypeLegalEnum.RESIDENT);
        } else {
            company.setTypeLegal(TypeLegalEnum.NO_RESIDENT);
        }
        return company;
    }
}
