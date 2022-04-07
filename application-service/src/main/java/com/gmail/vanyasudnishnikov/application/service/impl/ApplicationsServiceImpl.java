package com.gmail.vanyasudnishnikov.application.service.impl;

import com.gmail.vanyasudnishnikov.application.repository.ApplicationDetailsRepository;
import com.gmail.vanyasudnishnikov.application.repository.ApplicationRepository;
import com.gmail.vanyasudnishnikov.application.repository.model.Application;
import com.gmail.vanyasudnishnikov.application.repository.model.ApplicationDetails;
import com.gmail.vanyasudnishnikov.application.repository.model.StatusEnum;
import com.gmail.vanyasudnishnikov.application.service.ApplicationsService;
import com.gmail.vanyasudnishnikov.application.repository.feign.EmployeeFeign;
import com.gmail.vanyasudnishnikov.application.service.model.ApplicationDTO;
import com.gmail.vanyasudnishnikov.application.service.model.ViewEmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class ApplicationsServiceImpl implements ApplicationsService {
    private ApplicationRepository applicationRepository;
    private EmployeeFeign employeeFeign;
    private ApplicationDetailsRepository applicationDetailsRepository;

    @Override
    @Transactional
    public Boolean add(List<ApplicationDTO> applications) {
        List<Application> applicationList = convertToApplication(applications);
        for (Application application : applicationList) {
            Optional<Application> optionalApplication = applicationRepository.add(application);
            if (optionalApplication.isEmpty()) {
                return false;
            }
            Application addedApplication = optionalApplication.get();
            ApplicationDetails applicationDetails = createApplicationDetails(addedApplication);
            Optional<ApplicationDetails> addedApplicationDetails = applicationDetailsRepository.add(applicationDetails);
            if (addedApplicationDetails.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean isUnique(List<ApplicationDTO> applications) {
        List<String> uuidList = new ArrayList<>();
        for (ApplicationDTO application : applications) {
            String uuid = application.getUuid();
            uuidList.add(uuid);
        }
        List<Application> applicationList = applicationRepository.findByUUID(uuidList);
        if (applicationList.isEmpty()) {
            return true;
        }
        return false;
    }

    private List<Application> convertToApplication(List<ApplicationDTO> applications) throws IllegalArgumentException {
        List<Application> applicationList = new ArrayList<>();
        for (ApplicationDTO applicationDTO : applications) {
            Application application = new Application();
            String uuid = applicationDTO.getUuid();
            application.setApplicationConvId(uuid);
            application.setStatus(StatusEnum.NEW);
            String valueLeg = applicationDTO.getValueLeg();
            application.setValueLeg(valueLeg);
            String valueInd = applicationDTO.getValueInd();
            application.setValueInd(valueInd);
            String employeeDTOId = applicationDTO.getEmployeeId();
            application.setEmployeeId(employeeDTOId);
            Long employeeId = Long.valueOf(employeeDTOId);
            Optional<ViewEmployeeDTO> employee = employeeFeign.getEmployeeById(employeeId);
            if (employee.isEmpty()) {
                throw new IllegalArgumentException();
            }
            String nameLegal = employee.get().getNameLegal();
            application.setNameLegal(nameLegal);
            String percentConv = applicationDTO.getPercentConv();
            Float percentConverter = Float.valueOf(percentConv);
            application.setPercentConv(percentConverter);
            applicationList.add(application);
        }
        return applicationList;
    }

    private ApplicationDetails createApplicationDetails(Application addedApplication) {
        ApplicationDetails applicationDetails = new ApplicationDetails();
        applicationDetails.setApplication(addedApplication);
        Date date = new Date();
        applicationDetails.setCreateDate(date);
        applicationDetails.setLastUpdate(date);
        applicationDetails.setNote("-");
        return applicationDetails;
    }
}
