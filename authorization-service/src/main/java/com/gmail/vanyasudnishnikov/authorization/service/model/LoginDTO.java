package com.gmail.vanyasudnishnikov.authorization.service.model;


import com.gmail.vanyasudnishnikov.authorization.service.validator.IsExistsUser;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Data
@Setter
public class LoginDTO {
    @IsExistsUser
    @NotNull(message = "Please enter a username.")
    private String login;
    @NotNull(message = "Please enter a password.")
    private String password;
}
