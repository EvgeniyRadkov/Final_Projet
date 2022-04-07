package com.gmail.vanyasudnishnikov.employee.repository.impl;

import com.gmail.vanyasudnishnikov.employee.repository.EmployeeDetailsRepository;
import com.gmail.vanyasudnishnikov.employee.repository.model.EmployeeDetails;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.PersistentObjectException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
public class EmployeeDetailsRepositoryImpl extends GenericRepositoryImpl<Long, EmployeeDetails> implements EmployeeDetailsRepository {
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
}
