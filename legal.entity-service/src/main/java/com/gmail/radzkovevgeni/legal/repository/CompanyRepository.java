package com.gmail.radzkovevgeni.legal.repository;

import com.gmail.radzkovevgeni.legal.repository.model.Company;

import java.util.List;

public interface CompanyRepository extends GenericRepository<Long, Company> {
    List<Company> find(String nameCompany, String unp, String ibanByBYN);

    List<Company> get(String nameCompany, String unp, String ibanByBYN);
}
