package com.gmail.vanyasudnishnikov.application.service.impl;

import com.gmail.vanyasudnishnikov.application.repository.ApplicationRepository;
import com.gmail.vanyasudnishnikov.application.repository.model.Application;
import com.gmail.vanyasudnishnikov.application.service.ApplicationService;
import com.gmail.vanyasudnishnikov.application.repository.feign.EmployeeFeign;
import com.gmail.vanyasudnishnikov.application.service.model.PaginationDTO;
import com.gmail.vanyasudnishnikov.application.service.model.PaginationEnum;
import com.gmail.vanyasudnishnikov.application.service.model.ViewApplicationDTO;
import com.gmail.vanyasudnishnikov.application.service.model.ViewEmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private static final Integer MIN_CUSTOM_PAGE = 20;
    private static final Integer AVERAGE_CUSTOM_PAGE = 50;
    private static final Integer MAX_CUSTOM_PAGE = 100;
    private ApplicationRepository applicationRepository;
    private EmployeeFeign employeeFeign;

    @Override
    @Transactional
    public List<ViewApplicationDTO> getAll(PaginationDTO paginationDTO, Pageable pageable, String customizedPage) {
        PaginationEnum pagination = paginationDTO.getPagination();
        if (pagination == PaginationEnum.Default) {
            List<Application> applications = applicationRepository.getAll(pageable);
            if (applications.isEmpty()) {
                return Collections.emptyList();
            }
            List<ViewApplicationDTO> viewApplicationDTOList = new ArrayList<>();
            for (Application application : applications) {
                ViewApplicationDTO viewApplicationDTO = convertToApplicationDTO(application);
                viewApplicationDTOList.add(viewApplicationDTO);
            }
            return viewApplicationDTOList;
        } else {
            if (customizedPage == null || customizedPage.equals("")) {
                return Collections.emptyList();
            }
            int customPage = Integer.parseInt(customizedPage);
            Integer page = paginationDTO.getPage();
            if (customPage == MIN_CUSTOM_PAGE || customPage == AVERAGE_CUSTOM_PAGE || customPage == MAX_CUSTOM_PAGE) {
                Pageable customPageable = PageRequest.of(page, customPage);
                List<Application> applications = applicationRepository.getAll(customPageable);
                if (applications.isEmpty()) {
                    return Collections.emptyList();
                }
                List<ViewApplicationDTO> viewApplicationDTOList = new ArrayList<>();
                for (Application application : applications) {
                    ViewApplicationDTO viewApplicationDTO = convertToApplicationDTO(application);
                    viewApplicationDTOList.add(viewApplicationDTO);
                }
                return viewApplicationDTOList;
            }
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional
    public Optional<ViewApplicationDTO> getById(Long applicationId) {
        Optional<Application> optionalEmployee = applicationRepository.findById(applicationId);
        if (optionalEmployee.isEmpty()) {
            return Optional.empty();
        }
        Application application = optionalEmployee.get();
        ViewApplicationDTO viewApplicationDTO = convertToApplicationDTO(application);
        return Optional.ofNullable(viewApplicationDTO);
    }

    private ViewApplicationDTO convertToApplicationDTO(Application application) {
        String employeeId = application.getEmployeeId();
        Integer id = Integer.valueOf(employeeId);
        Optional<ViewEmployeeDTO> employee = employeeFeign.getEmployeeById(Long.valueOf(id));
        String fullNameIndividual = employee.get().getFullNameIndividual();
        return ViewApplicationDTO.builder()
                .applicationConvId(Math.toIntExact(application.getId()))
                .status(String.valueOf(application.getStatus()))
                .employeeId(id)
                .fullNameIndividual(fullNameIndividual)
                .percentConv(application.getPercentConv())
                .valueLeg(application.getValueLeg())
                .valueInd(application.getValueInd())
                .nameLegal(application.getNameLegal())
                .build();
    }
}