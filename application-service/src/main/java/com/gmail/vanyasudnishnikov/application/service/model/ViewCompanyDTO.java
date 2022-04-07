package com.gmail.vanyasudnishnikov.application.service.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ViewCompanyDTO {
    private Long legalId;
    private String nameLegal;
    private Integer unp;
    private String ibanByBYN;
    private String typeLegal;
    private Integer totalEmployees;

    @Override
    public String toString() {
        return "ViewCompanyDTO{" +
                "LegalId=" + legalId +
                ", Name_Legal='" + nameLegal + '\'' +
                ", UNP=" + unp +
                ", IBANbyBYN='" + ibanByBYN + '\'' +
                ", Type_Legal='" + typeLegal + '\'' +
                ", Total_Employees=" + totalEmployees +
                '}';
    }
}
