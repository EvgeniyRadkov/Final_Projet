package com.gmail.vanyasudnishnikov.application.service.impl;

import com.gmail.vanyasudnishnikov.application.service.model.ApplicationDTO;
import com.gmail.vanyasudnishnikov.application.service.FileService;
import liquibase.util.csv.opencsv.bean.CsvToBean;
import liquibase.util.csv.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private static final String FIRST_FIELD_OF_FILE = "ApplicationConvId";
    private static final String SECOND_FIELD_OF_FILE = "Value_Leg";
    private static final String THIRD_FIELD_OF_FILE = "Value_Ind";
    private static final String FOURTH_FIELD_OF_FILE = "EmployeeId";
    private static final String FIFTH_FIELD_OF_FILE = "Percent_Conv";
    private static final String SIXTH_FIELD_OF_FILE = "Note";
    private static final String UUID_PARAMETER = "uuid";
    private static final String VALUE_LEG_PARAMETER = "valueLeg";
    private static final String VALUE_IND_PARAMETER = "valueInd";
    private static final String EMPLOYEE_ID_PARAMETER = "employeeId";
    private static final String PERCENT_CONV_PARAMETER = "percentConv";
    private static final String NOTE_PARAMETER = "note";

    @Override
    public List<ApplicationDTO> createApplicationFromFile(MultipartFile file) {
        try {
            Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            CsvToBean<ApplicationDTO> csvToBean = new CsvToBean<>();
            Map<String, String> columnMapping = new HashMap<>();
            columnMapping.put(FIRST_FIELD_OF_FILE, UUID_PARAMETER);
            columnMapping.put(SECOND_FIELD_OF_FILE, VALUE_LEG_PARAMETER);
            columnMapping.put(THIRD_FIELD_OF_FILE, VALUE_IND_PARAMETER);
            columnMapping.put(FOURTH_FIELD_OF_FILE, EMPLOYEE_ID_PARAMETER);
            columnMapping.put(FIFTH_FIELD_OF_FILE, PERCENT_CONV_PARAMETER);
            columnMapping.put(SIXTH_FIELD_OF_FILE, NOTE_PARAMETER);
            HeaderColumnNameTranslateMappingStrategy<ApplicationDTO> strategy = new HeaderColumnNameTranslateMappingStrategy<>();
            strategy.setType(ApplicationDTO.class);
            strategy.setColumnMapping(columnMapping);
            return csvToBean.parse(strategy, reader);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }
}
