package com.gmail.vanyasudnishnikov.application.repository;

import com.gmail.vanyasudnishnikov.application.repository.model.Application;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ApplicationRepository extends GenericRepository<Long, Application> {
    List<Application> findByUUID(List<String> uuid);

    List<Application> getAll(Pageable pageable);
}
