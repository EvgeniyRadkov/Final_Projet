package com.gmail.vanyasudnishnikov.application.service;

import com.gmail.vanyasudnishnikov.application.service.model.ApplicationDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    List<ApplicationDTO> createApplicationFromFile(MultipartFile file);
}
