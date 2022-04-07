package com.gmail.vanyasudnishnikov.application.service;

import com.gmail.vanyasudnishnikov.application.service.model.ViewStatusDTO;

import java.util.Optional;

public interface StatusService {

    Optional<ViewStatusDTO> changeInApplication(Long applicationId, String status);
}
