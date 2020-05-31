package com.Clinic_Accounting_System.Clinic_Accounting_System.models;

import javax.persistence.*;
import javax.validation.Constraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "appointments")
public class Appointments implements Serializable {

    @EmbeddedId
    private AppointmentID appointmentID;

    @Size(max = 50)
    @Column(name = "comment", columnDefinition = "varchar(50)")
    private String comment;

    public Appointments() { }

    public Appointments(AppointmentID appointmentID, @Size(max = 50) String comment) {
        this.appointmentID = appointmentID;
        this.comment = comment;
    }

    public AppointmentID getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(AppointmentID appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointments)) return false;
        Appointments that = (Appointments) o;
        return Objects.equals(getAppointmentID(), that.getAppointmentID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAppointmentID());
    }
}