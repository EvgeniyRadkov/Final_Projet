package com.gmail.vanyasudnishnikov.employee.repository.impl;

import com.gmail.vanyasudnishnikov.employee.repository.EmployeeRepository;
import com.gmail.vanyasudnishnikov.employee.repository.model.Employee;
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
public class EmployeeRepositoryImpl extends GenericRepositoryImpl<Long, Employee> implements EmployeeRepository {
    @Override
    public Optional<Employee> add(Employee employee) {
        try {
            em.persist(employee);
            return Optional.of(employee);
        } catch (PersistentObjectException e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Employee> findById(Long id) {
        String hql = "select e from Employee as e where e.id=:employeeId";
        Query query = em.createQuery(hql);
        query.setParameter("employeeId", id);
        try {
            Employee company = (Employee) query.getSingleResult();
            return Optional.ofNullable(company);
        } catch (NoResultException e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public List<Employee> findAll(Pageable pageable) {
        String hql = "select e from Employee as e";
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
    public List<Employee> find(String name, String ibanByn, String ibanCurrency) {
        String hql = "select e from Employee as e where " +
                "e.nameEmployee=:name " +
                "or e.ibanByBYN=:ibanByn " +
                "or e.ibanByCurrency=:ibanCurrency";
        Query query = em.createQuery(hql);
        query.setParameter("name", name);
        query.setParameter("ibanByn", ibanByn);
        query.setParameter("ibanCurrency", ibanCurrency);
        try {
            List<Employee> employees = query.getResultList();
            return employees;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Employee> search(List<Long> legalIdList, String nameIndividual) {
        String hql = "select e from Employee as e where " +
                "e.nameEmployee like :name " +
                "and e.companyId in (:legalId) ";
        Query query = em.createQuery(hql);
        query.setParameter("name", nameIndividual + "%");
        query.setParameter("legalId", legalIdList);
        try {
            List<Employee> employees = query.getResultList();
            System.out.println(employees);
            return employees;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}
