package com.gmail.vanyasudnishnikov.application.repository.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "t_application")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f_id", unique = true)
    private Long id;
    @OneToOne(
            fetch = FetchType.LAZY,
            mappedBy = "application",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private ApplicationDetails applicationDetails;
    @Column(name = "f_application_conv_id", unique = true)
    private String applicationConvId;
    @Column(name = "f_status")
    @Enumerated(value = EnumType.STRING)
    private StatusEnum status;
    @Column(name = "f_value_leg")
    private String valueLeg;
    @Column(name = "f_value_ind")
    private String valueInd;
    @Column(name = "f_employee_id")
    private String employeeId;
    @Column(name = "f_percent_conv")
    private Float PercentConv;
    @Column(name = "f_name_legal")
    private String nameLegal;
}