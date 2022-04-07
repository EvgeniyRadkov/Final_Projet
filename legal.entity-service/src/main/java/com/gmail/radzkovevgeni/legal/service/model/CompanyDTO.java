package com.gmail.radzkovevgeni.legal.service.model;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class CompanyDTO {
    @NotBlank
    @Size(max = 255)
    private String nameLegal;
    @NotNull
    @Digits(integer = 9, fraction = 0)
    private Integer unp;
    @NotBlank
    @Size(max = 28)
    @Pattern(regexp = "^BY[0-9]{2}(UNBS)[0-9]{20}")
    private String ibanByBYN;
    @NotNull
    private LegalEnum typeLegal;
    @NotNull
    @Max(value = 1000)
    private Integer totalEmployees;
}
