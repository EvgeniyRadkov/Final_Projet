package com.gmail.vanyasudnishnikov.application.service.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ViewApplicationDTO {
    private Integer applicationConvId;
    private String status;
    private Integer employeeId;
    private String fullNameIndividual;
    private Float percentConv;
    private String valueLeg;
    private String valueInd;
    private String nameLegal;
}
