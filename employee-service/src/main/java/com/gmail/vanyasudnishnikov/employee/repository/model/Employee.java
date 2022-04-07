package com.gmail.vanyasudnishnikov.employee.repository.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "t_company_employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f_id", unique = true)
    private Long id;
    @OneToOne(
            fetch = FetchType.LAZY,
            mappedBy = "employee",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private EmployeeDetails employeeDetails;
    @Column(name = "f_full_name_individual", unique = true)
    private String nameEmployee;
    @Column(name = "f_recruitment_date")
    private Date recruitmentDate;
    @Column(name = "f_termination_date")
    private Date terminationDate;
    @Column(name = "f_person_iban_byn")
    private String ibanByBYN;
    @Column(name = "f_person_iban_currency")
    private String ibanByCurrency;
    @Column(name = "f_legal_id")
    private Long companyId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id)
                && Objects.equals(nameEmployee, employee.nameEmployee)
                && Objects.equals(recruitmentDate, employee.recruitmentDate)
                && Objects.equals(terminationDate, employee.terminationDate)
                && Objects.equals(ibanByBYN, employee.ibanByBYN)
                && Objects.equals(ibanByCurrency, employee.ibanByCurrency)
                && Objects.equals(companyId, employee.companyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameEmployee, recruitmentDate, terminationDate, ibanByBYN, ibanByCurrency, companyId);
    }
}
