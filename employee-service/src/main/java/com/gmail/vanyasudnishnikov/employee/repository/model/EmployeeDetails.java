package com.gmail.vanyasudnishnikov.employee.repository.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "t_company_employees_details")
public class EmployeeDetails {
    @GenericGenerator(
            name = "generator",
            strategy = "foreign",
            parameters = @Parameter(name = "property", value = "employee"))
    @Id
    @Column(name = "f_company_employees_id")
    @GeneratedValue(generator = "generator")
    private Long employeeId;
    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Employee employee;
    @Column(name = "f_create_date")
    private Date createDate;
    @Column(name = "f_last_update")
    private Date updateDate;
    @Column(name = "f_position_legal")
    @Enumerated(value = EnumType.STRING)
    private PositionEnum positionLegal;
    @Column(name = "f_note")
    private String note;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDetails employeeDetails = (EmployeeDetails) o;
        return Objects.equals(employeeId, employeeDetails.employeeId)
                && Objects.equals(positionLegal, employeeDetails.positionLegal)
                && Objects.equals(createDate, employeeDetails.createDate)
                && Objects.equals(updateDate, employeeDetails.updateDate)
                && Objects.equals(note, employeeDetails.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, positionLegal, createDate, updateDate, note);
    }
}
