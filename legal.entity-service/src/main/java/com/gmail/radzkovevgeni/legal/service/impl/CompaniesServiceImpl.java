package com.gmail.radzkovevgeni.legal.service.impl;

import com.gmail.radzkovevgeni.legal.repository.CompanyRepository;
import com.gmail.radzkovevgeni.legal.repository.model.Company;
import com.gmail.radzkovevgeni.legal.repository.model.CompanyDetails;
import com.gmail.radzkovevgeni.legal.repository.model.TypeLegalEnum;
import com.gmail.radzkovevgeni.legal.service.CompaniesService;
import com.gmail.radzkovevgeni.legal.service.model.FindCompanyDTO;
import com.gmail.radzkovevgeni.legal.service.model.PaginationDTO;
import com.gmail.radzkovevgeni.legal.service.model.PaginationEnum;
import com.gmail.radzkovevgeni.legal.service.model.ViewCompanyDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class CompaniesServiceImpl implements CompaniesService {
    private static final Integer MIN_CUSTOM_PAGE = 20;
    private static final Integer AVERAGE_CUSTOM_PAGE = 50;
    private static final Integer MAX_CUSTOM_PAGE = 100;
    private CompanyRepository companyRepository;

    @Override
    @Transactional
    public List<ViewCompanyDTO> getList(PaginationDTO paginationDTO, Pageable pageable) {
        String pagination = paginationDTO.getPagination();
        PaginationEnum paginationEnum = PaginationEnum.valueOf(pagination);
        if (paginationEnum == PaginationEnum.Default) {
            List<Company> companies = companyRepository.findAll(pageable);
            if (companies.isEmpty()) {
                return Collections.emptyList();
            }
            return convertToListDTO(companies);
        } else {
            String customizedPage = paginationDTO.getCustomized_page();
            if (customizedPage == null || customizedPage.equals("")) {
                return Collections.emptyList();
            }
            int customPage = Integer.parseInt(customizedPage);
            Integer page = paginationDTO.getPage();
            if (customPage == MIN_CUSTOM_PAGE || customPage == AVERAGE_CUSTOM_PAGE || customPage == MAX_CUSTOM_PAGE) {
                Pageable customPageable = PageRequest.of(page, customPage);
                List<Company> companies = companyRepository.findAll(customPageable);
                if (companies.isEmpty()) {
                    return Collections.emptyList();
                }
                return convertToListDTO(companies);
            }
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional
    public List<ViewCompanyDTO> find(FindCompanyDTO findCompanyDTO) {
        String nameCompany = findCompanyDTO.getName_Legal();
        String unp = findCompanyDTO.getUNP();
        String ibanByBYN = findCompanyDTO.getIBANbyBYN();
        List<Company> companies = companyRepository.find(nameCompany, unp, ibanByBYN);
        if (companies.isEmpty()) {
            return Collections.emptyList();
        }
        return convertToListDTO(companies);
    }

    private List<ViewCompanyDTO> convertToListDTO(List<Company> companyList) {
        List<ViewCompanyDTO> viewCompanyDTOList = new ArrayList<>();
        for (Company company : companyList) {
            String ibanByBYN = company.getIbanByBYN();
            TypeLegalEnum typeLegal = company.getTypeLegal();
            String typeCompany = String.valueOf(typeLegal);
            CompanyDetails companyDetails = company.getCompanyDetails();
            Integer totalEmployees = companyDetails.getTotalEmployees();
            ViewCompanyDTO viewCompanyDTO = ViewCompanyDTO.builder()
                    .legalId(company.getId())
                    .nameLegal(company.getName())
                    .unp(Integer.valueOf(company.getUnp()))
                    .ibanByBYN(ibanByBYN)
                    .typeLegal(typeCompany)
                    .totalEmployees(totalEmployees)
                    .build();
            viewCompanyDTOList.add(viewCompanyDTO);
        }
        return viewCompanyDTOList;
    }
}
