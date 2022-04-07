package com.gmail.vanyasudnishnikov.application.repository.impl;

import com.gmail.vanyasudnishnikov.application.repository.ApplicationRepository;
import com.gmail.vanyasudnishnikov.application.repository.model.Application;
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
public class ApplicationRepositoryImpl extends GenericRepositoryImpl<Long, Application> implements ApplicationRepository {
    @Override
    public Optional<Application> add(Application application) {
        try {
            em.persist(application);
            return Optional.of(application);
        } catch (PersistentObjectException e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Application> findById(Long applicationId) {
        String hql = "select a from Application as a where a.id=:applicationId";
        Query query = em.createQuery(hql);
        query.setParameter("applicationId", applicationId);
        try {
            Application company = (Application) query.getSingleResult();
            return Optional.ofNullable(company);
        } catch (NoResultException e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public Boolean update(Application application) {
        try {
            em.merge(application);
            return true;
        } catch (PersistentObjectException e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public List<Application> findByUUID(List<String> uuidList) {
        String hql = "select a from Application as a where a.applicationConvId in (:uuid)";
        Query query = em.createQuery(hql);
        query.setParameter("uuid", uuidList);
        try {
            List<Application> applications = query.getResultList();
            return applications;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Application> getAll(Pageable pageable) {
        String hql = "select a from Application as a";
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
}
