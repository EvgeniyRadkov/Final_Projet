package com.gmail.vanyasudnishnikov.authorization.repository.impl;


import com.gmail.vanyasudnishnikov.authorization.repository.EmployeeRepository;
import com.gmail.vanyasudnishnikov.authorization.repository.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.PersistentObjectException;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;


@Repository
@Slf4j
public class EmployeeRepositoryImpl extends GenericRepositoryImpl<String, Employee> implements EmployeeRepository {
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
    public Optional<Employee> findByName(String name) {
        String hql = "select e from Employee as e where e.username=:username";
        Query query = em.createQuery(hql);
        query.setParameter("username", name);
        try {
            Employee singleResult = (Employee) query.getSingleResult();
            return Optional.ofNullable(singleResult);
        } catch (NoResultException e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public Boolean update(Employee employee) {
        try {
            em.merge(employee);
            return true;
        } catch (PersistentObjectException e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Optional<Employee> findByUserMail(String userMail) {
        String hql = "select e from Employee as e where e.userMail=:userMail";
        Query query = em.createQuery(hql);
        query.setParameter("userMail", userMail);
        try {
            Employee singleResult = (Employee) query.getSingleResult();
            return Optional.ofNullable(singleResult);
        } catch (NoResultException e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }
}
