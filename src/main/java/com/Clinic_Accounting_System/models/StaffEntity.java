package com.Clinic_Accounting_System.Clinic_Accounting_System.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "staff_entity")
public class StaffEntity implements Serializable {

    @Id
    private Long id;

    @NotNull
    @Column(name = "salary", nullable = false)
    private double salary;

    // Specifying the child relationship with UserInfo datatable(owner of the relationship)
    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            optional = false)
    @JoinColumn(name = "id", nullable = false, unique = true)
    @MapsId
    private UserInfo userInfo;

    // Specifying the parent relationship with Doctors datatable
    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "staffEntity")
    private Doctors doctor;

    public StaffEntity() { }

    public StaffEntity(@NotNull double salary, UserInfo userInfo) {
        this.salary = salary;
        this.userInfo = userInfo;
    }

    public Long getId() {
        return id;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StaffEntity)) return false;
        StaffEntity that = (StaffEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}