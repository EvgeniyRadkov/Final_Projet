package com.gmail.radzkovevgeni.legal.repository.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "t_company_details")
public class CompanyDetails {
    @GenericGenerator(
            name = "generator",
            strategy = "foreign",
            parameters = @Parameter(name = "property", value = "company"))
    @Id
    @Column(name = "f_company_id")
    @GeneratedValue(generator = "generator")
    private Long companyId;
    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Company company;
    @Column(name = "f_total_employees")
    private Integer totalEmployees;
    @Column(name = "f_create_date")
    private String createDate;
    @Column(name = "f_last_update")
    private String updateDate;
    @Column(name = "f_note")
    private String note;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyDetails companyDetails = (CompanyDetails) o;
        return Objects.equals(companyId, companyDetails.companyId)
                && Objects.equals(totalEmployees, companyDetails.totalEmployees)
                && Objects.equals(createDate, companyDetails.createDate)
                && Objects.equals(updateDate, companyDetails.updateDate)
                && Objects.equals(note, companyDetails.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, totalEmployees, createDate, updateDate, note);
    }
}
