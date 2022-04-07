package com.gmail.vanyasudnishnikov.authorization.repository.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "t_employee_session")
public class EmployeeSession {
    @Id
    @Column(name = "f_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_employee_id", nullable = false)
    private Employee employee;
    @Column(name = "f_token")
    private String jwtToken;
    @Column(name = "f_session_start")
    private String sessionStart;
    @Column(name = "f_session_end")
    private String sessionEnd;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeSession employeeSession = (EmployeeSession) o;
        return Objects.equals(employeeId, employeeSession.employeeId)
                && Objects.equals(sessionStart, employeeSession.sessionStart)
                && Objects.equals(sessionEnd, employeeSession.sessionEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, sessionStart, sessionEnd);
    }
}
