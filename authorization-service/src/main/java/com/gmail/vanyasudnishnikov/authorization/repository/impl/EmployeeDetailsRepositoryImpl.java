package com.gmail.vanyasudnishnikov.authorization.repository.impl;


import com.gmail.vanyasudnishnikov.authorization.repository.EmployeeDetailsRepository;
import com.gmail.vanyasudnishnikov.authorization.repository.model.EmployeeDetails;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.PersistentObjectException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
public class EmployeeDetailsRepositoryImpl extends GenericRepositoryImpl<String, EmployeeDetails> implements EmployeeDetailsRepository {

    @Override
    public Optional<EmployeeDetails> add(EmployeeDetails employeeDetails) {
        try {
            em.persist(employeeDetails);
            return Optional.of(employeeDetails);
        } catch (PersistentObjectException e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public Boolean update(EmployeeDetails employeeDetails) {
        try {
            em.merge(employeeDetails);
            return true;
        } catch (PersistentObjectException e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }
}
