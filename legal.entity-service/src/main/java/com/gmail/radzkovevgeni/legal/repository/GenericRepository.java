package com.gmail.radzkovevgeni.legal.repository;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<I, T> {
    Optional<T> add(T entity);

    Optional<T> findById(I id);

    List<T> findAll(Pageable pageable);

    Optional<T> findByName(String name);

    Boolean update(T entity);

}
