package com.gmail.vanyasudnishnikov.application.repository.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "t_application_details")
public class ApplicationDetails {
    @GenericGenerator(
            name = "generator",
            strategy = "foreign",
            parameters = @Parameter(name = "property", value = "application"))
    @Id
    @Column(name = "f_application_id")
    @GeneratedValue(generator = "generator")
    private Long applicationId;
    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Application application;
    @Column(name = "f_create_date")
    private Date createDate;
    @Column(name = "f_last_update")
    private Date lastUpdate;
    @Column(name = "f_note")
    private String note;
}
