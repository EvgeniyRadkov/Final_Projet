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
@Table(name = "t_employee_details")
public class EmployeeDetails {
    @GenericGenerator(
            name = "generator",
            strategy = "foreign",
            parameters = @Parameter(name = "property", value = "employee"))
    @Id
    @Column(name = "f_employee_id")
    @GeneratedValue(generator = "generator")
    private Long employeeId;
    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Employee employee;
    @Column(name = "f_first_name")
    private String firstName;
    @Column(name = "f_creation_date")
    private String creationDate;
    @Column(name = "f_attempt")
    private Integer attempt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDetails employeeDetails = (EmployeeDetails) o;
        return Objects.equals(employeeId, employeeDetails.employeeId)
                && Objects.equals(firstName, employeeDetails.firstName)
                && Objects.equals(creationDate, employeeDetails.creationDate)
                && Objects.equals(attempt, employeeDetails.attempt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, firstName, creationDate, attempt);
    }
}
