package com.gmail.vanyasudnishnikov.authorization.repository.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "t_employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f_id", unique = true)
    private Long id;
    @OneToMany(
            mappedBy = "employee",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<EmployeeSession> employeeSession;
    @OneToOne(
            fetch = FetchType.LAZY,
            mappedBy = "employee",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private EmployeeDetails employeeDetails;
    @Column(name = "f_username", unique = true)
    private String username;
    @Column(name = "f_password")
    private String password;
    @Column(name = "f_user_mail")
    private String userMail;
    @Column(name = "f_status")
    @Enumerated(value = EnumType.STRING)
    private StatusEnum status;
    @Column(name = "f_role")
    @Enumerated(value = EnumType.STRING)
    private RoleEnum role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id)
                && Objects.equals(username, employee.username)
                && Objects.equals(password, employee.password)
                && Objects.equals(userMail, employee.userMail)
                && Objects.equals(status, employee.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, userMail, status);
    }
}
