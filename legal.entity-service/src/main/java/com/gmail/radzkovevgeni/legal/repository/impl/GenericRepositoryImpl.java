package com.gmail.radzkovevgeni.legal.repository.impl;


import com.gmail.radzkovevgeni.legal.repository.GenericRepository;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public class GenericRepositoryImpl<I, T> implements GenericRepository<I, T> {
    protected Class<T> clazz;
    @PersistenceContext
    protected EntityManager em;

    public GenericRepositoryImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.clazz = (Class<T>) genericSuperclass.getActualTypeArguments()[1];
    }

    @Override
    public Optional<T> add(T entity) {
        em.persist(entity);
        return (Optional<T>) entity;
    }

    @Override
    public Optional<T> findById(I id) {
        return Optional.empty();
    }

    @Override
    public List<T> findAll(Pageable pageable) {
        String queryString = "from" + clazz.getName();
        Query query = em.createQuery(queryString);
        return query.getResultList();
    }

    @Override
    public Optional<T> findByName(String name) {
        return (Optional<T>) em.find(clazz, name);
    }

    @Override
    public Boolean update(T entity) {
        return true;
    }
}
