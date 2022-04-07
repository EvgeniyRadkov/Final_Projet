package com.gmail.vanyasudnishnikov.application.service.model;

import liquibase.util.csv.opencsv.bean.CsvBind;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class ApplicationDTO {
    @CsvBind
    @Pattern(regexp = "([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})")
    private String uuid;
    @CsvBind
    @Pattern(regexp = "^BYN(/)[0-9]{3}")
    private String valueLeg;
    @CsvBind
    private String valueInd;
    @CsvBind
    private String employeeId;
    @CsvBind
    private String percentConv;
    @CsvBind
    private String note;
}