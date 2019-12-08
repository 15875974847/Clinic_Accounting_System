package com.Clinic_Accounting_System.Clinic_Accounting_System.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

@Embeddable
public class AppointmentID implements Serializable {

    @NotNull
    @Column(name = "date", nullable = false)
    private Date date;

    @NotNull
    @Column(name = "number_in_queue", nullable = false)
    private int numberInQueue;

    // specifying many to one relationship to UserInfo table(btw, it's unidirectional relationship)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = false)
    private UserInfo patient;

    // specifying many to one relationship to Doctors table(btw, it's unidirectional relationship)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id", nullable = false)
    private Doctors doctor;

    public AppointmentID() {}

    public AppointmentID(UserInfo patient, Doctors doctor, @NotNull Date date, @NotNull int numberInQueue) {
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.numberInQueue = numberInQueue;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNumberInQueue() {
        return numberInQueue;
    }

    public void setNumberInQueue(byte numberInQueue) {
        this.numberInQueue = numberInQueue;
    }

    public UserInfo getPatient() {
        return patient;
    }

    public void setPatient(UserInfo patient) {
        this.patient = patient;
    }

    public Doctors getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctors doctor) {
        this.doctor = doctor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppointmentID)) return false;
        AppointmentID that = (AppointmentID) o;
        return
                Objects.equals(getDate(), that.getDate()) &&
                        Objects.equals(getNumberInQueue(), that.getNumberInQueue()) &&
                        Objects.equals(getDoctor(), that.getDoctor()) &&
                        Objects.equals(getPatient(), that.getPatient());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDate(), getNumberInQueue(), getDoctor(), getPatient());
    }

}