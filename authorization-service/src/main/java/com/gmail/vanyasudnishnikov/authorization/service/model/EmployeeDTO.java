package com.gmail.vanyasudnishnikov.authorization.service.model;

import com.gmail.vanyasudnishnikov.authorization.service.validator.InCyrillic;
import com.gmail.vanyasudnishnikov.authorization.service.validator.UniqueUsername;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class EmployeeDTO {
    @UniqueUsername
    @Size(min = 6, max = 100, message = "Enter a password between 6 and 100 characters.")
    private String username;
    @Size(min = 9, max = 20, message = "Enter a password between 9 and 20 characters.")
    private String password;
    @Email(regexp = "\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}", message = "The mail is not valid")
    private String usermail;
    @InCyrillic
    @Size(max = 20, message = "Number of letters exceeded")
    private String firstName;
}
