package com.Clinic_Accounting_System.Clinic_Accounting_System.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
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
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private UserInfo patient;

    // specifying many to one relationship to Doctors table(btw, it's unidirectional relationship)
    /*
        ** Gotchas **
        In this case all cascade type crap did not work and causing ConstraintViolationException on parent deletion
        As far as i understand, this is hibernate don't know what childs parent have and should to delete because it's unidirectional binding on the side of child, so childs become orphans
        And it's unacceptable, because those fields are part of composite PK, so they cannot be NULL
        So, that's why we are catching ConstraintViolationException
        Why i'm leaving Cascade property here? - In case if i'll decide to change unidirectional relationship on bidirectional
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
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

    public void setNumberInQueue(int numberInQueue) {
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
                Objects.equals(getDate().toString(), that.getDate().toString()) &&
                        Objects.equals(getNumberInQueue(), that.getNumberInQueue()) &&
                        Objects.equals(getDoctor(), that.getDoctor()) &&
                        Objects.equals(getPatient(), that.getPatient());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDate(), getNumberInQueue(), getDoctor(), getPatient());
    }

}