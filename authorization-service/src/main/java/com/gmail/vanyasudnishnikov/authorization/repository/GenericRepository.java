package com.gmail.vanyasudnishnikov.authorization.repository;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<I, T> {
    Optional<T> add(T entity);

    List<T> findById(I id);

    List<T> findAll();

    Optional<T> findByName(I name);

    Boolean update(T entity);

}
