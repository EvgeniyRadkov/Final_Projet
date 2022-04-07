package com.gmail.vanyasudnishnikov.employee.service.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class EmployeeDTO {
    @NotBlank
    @Size(max = 255)
    private String fullNameIndividual;
    @NotNull
    @Pattern(regexp = "[0-9]{2}(/)[0-9]{2}(/)[0-9]{4}")
    private String recruitmentDate;
    @NotNull
    @Pattern(regexp = "[0-9]{2}(/)[0-9]{2}(/)[0-9]{4}")
    private String terminationDate;
    @NotNull
    private String nameLegal;
    @NotBlank
    @Size(max = 28)
    @Pattern(regexp = "^BY[0-9]{2}(UNBS)[0-9]{20}")
    private String personIbanByn;
    @NotBlank
    @Size(max = 28)
    @Pattern(regexp = "^BY[0-9]{2}(UNBS)[0-9]{20}")
    private String personIbanCurrency;
}
