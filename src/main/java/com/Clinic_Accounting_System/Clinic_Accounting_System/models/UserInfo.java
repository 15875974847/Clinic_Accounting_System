package com.Clinic_Accounting_System.Clinic_Accounting_System.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user_info")
public class UserInfo implements Serializable {

    @Id
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "`first_name`", columnDefinition = "varchar(20)", nullable = false)
    private String firstName;

    @NotNull
    @Size(max = 20)
    @Column(name = "`middle_name`", columnDefinition = "varchar(20)", nullable = false)
    private String middleName;

    @NotNull
    @Size(max = 20)
    @Column(name = "`last_name`", columnDefinition = "varchar(20)", nullable = false)
    private String lastName;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dateOfBirth;

    @NotNull
    @Size(max = 40)
    @Column(name = "`address`", columnDefinition = "varchar(40)", nullable = false)
    private String address;

    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(name = "medical_history")
    private String medicalHistory;

    // Specifying the child relationship with Users datatable(owner of the relationship)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", nullable = false, unique = true)
    @MapsId
    private Users user;

    // Specifying the parent relationship with StaffEntity datatable
    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "userInfo")
    private StaffEntity staffEntity;

    public UserInfo() { }

    public UserInfo(@NotNull @Size(max = 20) String firstName, @NotNull @Size(max = 20) String middleName, @NotNull @Size(max = 20) String lastName, @NotNull Date dateOfBirth, @NotNull @Size(max = 40) String address, String medicalHistory, Users user) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.medicalHistory = medicalHistory;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
