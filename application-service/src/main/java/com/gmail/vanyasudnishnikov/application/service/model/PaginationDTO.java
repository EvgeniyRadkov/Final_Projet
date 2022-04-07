package com.gmail.vanyasudnishnikov.application.service.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PaginationDTO {
    @NotNull
    private PaginationEnum pagination;
    @NotNull
    private Integer page;
}
