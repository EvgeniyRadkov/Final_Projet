package com.gmail.radzkovevgeni.legal.repository.impl;

import com.gmail.radzkovevgeni.legal.repository.CompanyDetailsRepository;
import com.gmail.radzkovevgeni.legal.repository.model.Company;
import com.gmail.radzkovevgeni.legal.repository.model.CompanyDetails;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.PersistentObjectException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
public class CompanyDetailsRepositoryImpl extends GenericRepositoryImpl<Long, CompanyDetails> implements CompanyDetailsRepository {
    @Override
    public Optional<CompanyDetails> add(CompanyDetails companyDetails) {
        try {
            em.persist(companyDetails);
            return Optional.of(companyDetails);
        } catch (PersistentObjectException e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }
}
