package com.gmail.vanyasudnishnikov.employee.service.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ViewEmployeeDTO {
    private Long employeeId;
    private String fullNameIndividual;
    private String recruitmentDate;
    private String terminationDate;
    private String nameLegal;
    private String personIbanByn;
    private String personIbanCurrency;

    @Override
    public String toString() {
        return "ViewEmployeeDTO{" +
                "Employee_Id=" + employeeId +
                ", Full_Name_Individual='" + fullNameIndividual + '\'' +
                ", Recruitment_date='" + recruitmentDate + '\'' +
                ", Termination_date='" + terminationDate + '\'' +
                ", Name_Legal='" + nameLegal + '\'' +
                ", Person_Iban_Byn='" + personIbanByn + '\'' +
                ", Person_Iban_Currency='" + personIbanCurrency + '\'' +
                '}';
    }
}
