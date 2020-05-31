package com.Clinic_Accounting_System.Clinic_Accounting_System.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "doctors")
public class Doctors implements Serializable {

    @Id
    private Long id;

    @NotNull
    @Size(max = 40)
    @Column(name = "`degree`", columnDefinition = "varchar(40)", nullable = false)
    private String degree;

    @NotNull
    @Size(max = 40)
    @Column(name = "`specialization`", columnDefinition = "varchar(40)", nullable = false)
    private String specialization;

    // Specifying the child relationship with StaffEntity datatable(owner of the relationship)
    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            optional = false)
    @JoinColumn(name = "id", nullable = false, unique = true)
    @MapsId
    private StaffEntity staffEntity;

    public Doctors() {}

    public Doctors(@NotNull @Size(max = 40) String degree, @NotNull @Size(max = 40) String specialization, StaffEntity staffEntity) {
        this.degree = degree;
        this.specialization = specialization;
        this.staffEntity = staffEntity;
    }

    public Long getId() {
        return id;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public StaffEntity getStaffEntity() {
        return staffEntity;
    }

    public void setStaffEntity(StaffEntity staffEntity) {
        this.staffEntity = staffEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctors)) return false;
        Doctors that = (Doctors) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}