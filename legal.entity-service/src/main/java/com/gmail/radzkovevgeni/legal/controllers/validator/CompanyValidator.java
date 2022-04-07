package com.gmail.radzkovevgeni.legal.controllers.validator;

import com.gmail.radzkovevgeni.legal.repository.CompanyRepository;
import com.gmail.radzkovevgeni.legal.repository.model.Company;
import com.gmail.radzkovevgeni.legal.service.model.CompanyDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
@AllArgsConstructor
public class CompanyValidator implements Validator {
    private CompanyRepository companyRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        CompanyDTO companyDTO = (CompanyDTO) target;
        String nameLegal = companyDTO.getNameLegal();
        String unp = String.valueOf(companyDTO.getUnp());
        String ibanByBYN = companyDTO.getIbanByBYN();
        List<Company> companies = companyRepository.get(nameLegal, unp, ibanByBYN);
        if (!companies.isEmpty()) {
            errors.reject("error");
            errors.pushNestedPath(nameLegal + "," + unp + "," + ibanByBYN);
        }
    }
}
