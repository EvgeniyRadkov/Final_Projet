package com.gmail.vanyasudnishnikov.application.service.impl;

import com.gmail.vanyasudnishnikov.application.repository.ApplicationRepository;
import com.gmail.vanyasudnishnikov.application.repository.model.Application;
import com.gmail.vanyasudnishnikov.application.repository.model.StatusEnum;
import com.gmail.vanyasudnishnikov.application.service.StatusService;
import com.gmail.vanyasudnishnikov.application.service.model.ViewStatusDTO;
import com.gmail.vanyasudnishnikov.application.service.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class StatusServiceImpl implements StatusService {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_TOKEN = "Bearer ";
    private ApplicationRepository applicationRepository;
    private HttpServletRequest request;
    private JwtUtil jwtUtil;

    @Override
    @Transactional
    public Optional<ViewStatusDTO> changeInApplication(Long applicationId, String status) throws IllegalArgumentException {
        Optional<Application> optionalEmployee = applicationRepository.findById(applicationId);
        if (optionalEmployee.isEmpty()) {
            return Optional.empty();
        }
        ViewStatusDTO viewStatusDTO = new ViewStatusDTO();
        viewStatusDTO.setStatus(status);
        String headerAuth = request.getHeader(AUTHORIZATION_HEADER);
        String jwt = headerAuth.substring(BEARER_TOKEN.length());
        String username = jwtUtil.getUsernameFromJwtToken(jwt);
        viewStatusDTO.setBankEmployeeName(username);
        Application application = optionalEmployee.get();
        StatusEnum statusEnum = getStatusEnum(status);
        StatusEnum applicationStatus = application.getStatus();
        switch (Objects.requireNonNull(statusEnum)) {
            case REJECTED:
                if (applicationStatus == StatusEnum.NEW) {
                    application.setStatus(statusEnum);
                    Boolean update = applicationRepository.update(application);
                    if (!update) {
                        throw new IllegalArgumentException("Failed to change the status of the request.");
                    }
                } else {
                    return Optional.empty();
                }
            case IN_PROGRESS:
                if (applicationStatus == StatusEnum.NEW) {
                    application.setStatus(statusEnum);
                    Boolean update = applicationRepository.update(application);
                    if (!update) {
                        throw new IllegalArgumentException("Failed to change the status of the request.");
                    }
                    return Optional.of(viewStatusDTO);
                } else {
                    return Optional.empty();
                }
            case DONE:
                if (applicationStatus == StatusEnum.IN_PROGRESS) {
                    application.setStatus(statusEnum);
                    Boolean update = applicationRepository.update(application);
                    if (!update) {
                        throw new IllegalArgumentException("Failed to change the status of the request.");
                    }
                    return Optional.of(viewStatusDTO);
                } else {
                    return Optional.empty();
                }
            default:
                return Optional.empty();
        }
    }

    private StatusEnum getStatusEnum(String status) {
        String statusNew = StatusEnum.NEW.getLine();
        if (statusNew.equals(status)) {
            return StatusEnum.NEW;
        }
        String statusInProgress = StatusEnum.IN_PROGRESS.getLine();
        if (statusInProgress.equals(status)) {
            return StatusEnum.IN_PROGRESS;
        }
        String statusDone = StatusEnum.DONE.getLine();
        if (statusDone.equals(status)) {
            return StatusEnum.DONE;
        }
        String statusRejected = StatusEnum.REJECTED.getLine();
        if (statusRejected.equals(status)) {
            return StatusEnum.REJECTED;
        }
        return null;
    }
}

