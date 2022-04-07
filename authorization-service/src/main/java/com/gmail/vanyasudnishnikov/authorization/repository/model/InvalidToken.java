package com.gmail.vanyasudnishnikov.authorization.repository.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "t_token_black_list")
public class InvalidToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f_id", unique = true)
    private Long id;
    @Column(name = "f_closing_date", unique = true)
    private String closingDate;
    @Column(name = "f_jwt_token")
    private String jwtToken;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvalidToken invalidToken = (InvalidToken) o;
        return Objects.equals(id, invalidToken.id)
                && Objects.equals(closingDate, invalidToken.closingDate)
                && Objects.equals(jwtToken, invalidToken.jwtToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, closingDate, jwtToken);
    }
}
