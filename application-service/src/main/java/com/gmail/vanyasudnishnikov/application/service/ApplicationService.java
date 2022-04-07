package com.gmail.vanyasudnishnikov.application.service;

import com.gmail.vanyasudnishnikov.application.service.model.PaginationDTO;
import com.gmail.vanyasudnishnikov.application.service.model.ViewApplicationDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ApplicationService {
    List<ViewApplicationDTO> getAll(PaginationDTO paginationDTO, Pageable pageable, String customizedPage);

    Optional<ViewApplicationDTO> getById(Long applicationId);
}
