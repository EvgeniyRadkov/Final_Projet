package com.gmail.vanyasudnishnikov.application.service;

import com.gmail.vanyasudnishnikov.application.service.model.ApplicationDTO;

import java.util.List;

public interface ApplicationsService {
    Boolean add(List<ApplicationDTO> applications);

    Boolean isUnique(List<ApplicationDTO> applications);
}
