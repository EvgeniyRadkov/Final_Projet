package com.gmail.radzkovevgeni.legal.repository.impl;

import com.gmail.radzkovevgeni.legal.repository.CompanyRepository;
import com.gmail.radzkovevgeni.legal.repository.model.Company;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.PersistentObjectException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class CompanyRepositoryImpl extends GenericRepositoryImpl<Long, Company> implements CompanyRepository {
    @Override
    public Optional<Company> add(Company company) {
        try {
            em.persist(company);
            return Optional.of(company);
        } catch (PersistentObjectException e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Company> findById(Long id) {
        String hql = "select c from Company as c where c.id=:companyId";
        Query query = em.createQuery(hql);
        query.setParameter("companyId", id);
        try {
            Company company = (Company) query.getSingleResult();
            return Optional.ofNullable(company);
        } catch (NoResultException e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public List<Company> findAll(Pageable pageable) {
        String hql = "select l from Company as l";
        Query query = em.createQuery(hql);
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);
        try {
            return query.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Company> findByName(String name) {
        String hql = "select c from Company as c where c.name=:companyName";
        Query query = em.createQuery(hql);
        query.setParameter("companyName", name);
        try {
            Company company = (Company) query.getSingleResult();
            return Optional.ofNullable(company);
        } catch (NoResultException e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public List<Company> find(String nameCompany, String unp, String ibanByBYN) {
        String hql = "select c from Company as c where c.id is not null ";
        if ( nameCompany != null) {
            hql += " and c.name like :nameCompany";
        }
        if (unp != null) {
            hql += " and c.unp like :unp";
        }
        if (ibanByBYN != null) {
            hql += " and c.ibanByBYN like :ibanByBYN";
        }
        Query query = em.createQuery(hql);
        if (nameCompany != null) {
            query.setParameter("nameCompany", nameCompany + "%");
        }
        if (unp != null) {
            query.setParameter("unp", unp + "%");
        }
        if (ibanByBYN != null) {
            query.setParameter("ibanByBYN", ibanByBYN + "%");
        }
        try {
            List<Company> companyList = query.getResultList();
            return companyList;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Company> get(String nameCompany, String unp, String ibanByBYN) {
        String hql = "select c from Company as c where " +
                "c.name=:nameCompany " +
                "or c.unp=:unp " +
                "or c.ibanByBYN=:ibanByBYN";
        Query query = em.createQuery(hql);
        query.setParameter("nameCompany", nameCompany);
        query.setParameter("unp", unp);
        query.setParameter("ibanByBYN", ibanByBYN);
        try {
            List<Company> companyList = query.getResultList();
            return companyList;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}
