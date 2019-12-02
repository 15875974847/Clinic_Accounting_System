package com.Clinic_Accounting_System.Clinic_Accounting_System.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "doctors")
public class Doctors implements Serializable {

    @Id
    Long id;

    @NotNull
    @Size(max = 40)
    @Column(name = "`degree`", columnDefinition = "varchar(40)", nullable = false)
    private String middleName;

    @NotNull
    @Size(max = 40)
    @Column(name = "`specialization`", columnDefinition = "varchar(40)", nullable = false)
    private String lastName;

    // Specifying the child relationship with StaffEntity datatable(owner of the relationship)
    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            optional = false)
    @JoinColumn(name = "id", nullable = false, unique = true)
    @MapsId
    private StaffEntity staffEntity;

    public Doctors() {}

    public Doctors(@NotNull @Size(max = 40) String middleName, @NotNull @Size(max = 40) String lastName, StaffEntity staffEntity) {
        this.middleName = middleName;
        this.lastName = lastName;
        this.staffEntity = staffEntity;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public StaffEntity getStaffEntity() {
        return staffEntity;
    }

    public void setStaffEntity(StaffEntity staffEntity) {
        this.staffEntity = staffEntity;
    }
}
