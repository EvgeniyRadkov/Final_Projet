package com.gmail.vanyasudnishnikov.authorization.service.model;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class ViewEmployeeDTO {
    private Long userId;
    private String status;
}
