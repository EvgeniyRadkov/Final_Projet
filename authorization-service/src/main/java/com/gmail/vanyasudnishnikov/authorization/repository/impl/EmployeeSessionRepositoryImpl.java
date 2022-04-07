package com.gmail.vanyasudnishnikov.authorization.repository.impl;

import com.gmail.vanyasudnishnikov.authorization.repository.EmployeeSessionRepository;
import com.gmail.vanyasudnishnikov.authorization.repository.model.EmployeeSession;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.PersistentObjectException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
public class EmployeeSessionRepositoryImpl extends GenericRepositoryImpl<String, EmployeeSession> implements EmployeeSessionRepository {
    @Override
    public Boolean update(EmployeeSession employeeSession) {
        try {
            em.merge(employeeSession);
            return true;
        } catch (PersistentObjectException e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Optional<EmployeeSession> add(EmployeeSession employeeSession) {
        try {
            em.persist(employeeSession);
            return Optional.of(employeeSession);
        } catch (PersistentObjectException e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }
}
