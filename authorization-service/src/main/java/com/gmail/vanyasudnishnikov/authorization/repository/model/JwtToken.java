package com.gmail.vanyasudnishnikov.authorization.repository.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Setter
@Getter
public class JwtToken {
    private Date sessionStart;
    private Date sessionEnd;
    private String jwtToken;

}
