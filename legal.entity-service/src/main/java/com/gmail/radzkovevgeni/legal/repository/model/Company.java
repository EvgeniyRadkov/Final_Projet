package com.gmail.radzkovevgeni.legal.repository.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "t_company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f_id", unique = true)
    private Long id;
    @OneToOne(
            fetch = FetchType.LAZY,
            mappedBy = "company",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private CompanyDetails companyDetails;
    @Column(name = "f_name_company", unique = true)
    private String name;
    @Column(name = "f_unp")
    private String unp;
    @Column(name = "f_iban_by_byn")
    private String ibanByBYN;
    @Column(name = "f_type_legal")
    @Enumerated(value = EnumType.STRING)
    private TypeLegalEnum typeLegal;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company employee = (Company) o;
        return Objects.equals(id, employee.id)
                && Objects.equals(name, employee.name)
                && Objects.equals(unp, employee.unp)
                && Objects.equals(ibanByBYN, employee.ibanByBYN)
                && Objects.equals(typeLegal, employee.typeLegal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, unp, ibanByBYN, typeLegal);
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", companyDetails=" + companyDetails +
                ", name='" + name + '\'' +
                ", unp=" + unp +
                ", ibanByBYN='" + ibanByBYN + '\'' +
                ", typeLegal=" + typeLegal +
                '}';
    }
}
