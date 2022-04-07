package com.gmail.vanyasudnishnikov.authorization.service.model;

import com.gmail.vanyasudnishnikov.authorization.repository.model.RoleEnum;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Builder
@Getter
public class EmployeeSecurityDTO {
    private Long id;
    private String username;
    private String password;
    private String status;
    private List<RoleEnum> role;
}
