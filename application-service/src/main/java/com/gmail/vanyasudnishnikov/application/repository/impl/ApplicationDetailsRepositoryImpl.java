package com.gmail.vanyasudnishnikov.application.repository.impl;

import com.gmail.vanyasudnishnikov.application.repository.ApplicationDetailsRepository;
import com.gmail.vanyasudnishnikov.application.repository.GenericRepository;
import com.gmail.vanyasudnishnikov.application.repository.model.Application;
import com.gmail.vanyasudnishnikov.application.repository.model.ApplicationDetails;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.PersistentObjectException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
public class ApplicationDetailsRepositoryImpl extends GenericRepositoryImpl<Long, ApplicationDetails> implements ApplicationDetailsRepository {
    @Override
    public Optional<ApplicationDetails> add(ApplicationDetails applicationDetails) {
        try {
            em.persist(applicationDetails);
            return Optional.of(applicationDetails);
        } catch (PersistentObjectException e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }
}
