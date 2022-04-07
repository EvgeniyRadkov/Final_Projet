package com.gmail.radzkovevgeni.legal.service.model;

import com.gmail.radzkovevgeni.legal.service.validator.IsValidPagination;
import lombok.Data;


import javax.validation.constraints.NotNull;

@Data
public class PaginationDTO {
    @NotNull
    @IsValidPagination
    private String pagination;
    @NotNull
    private Integer page;
    private String customized_page;
}
