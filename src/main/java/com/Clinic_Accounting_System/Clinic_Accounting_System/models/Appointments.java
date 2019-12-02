package com.Clinic_Accounting_System.Clinic_Accounting_System.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "comments")
public class Appointments implements Serializable {

    @EmbeddedId
    private AppointmentID appointmentID;

    @Size(max = 20)
    @Column(name = "comment", columnDefinition = "varchar(20)")
    private String comment;
// TODO!!!!!!!!!!!!!!
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(
                    name = "company_id",
                    referencedColumnName = "company_id"),
            @JoinColumn(
                    name = "employee_number",
                    referencedColumnName = "employee_number")
    })

    public Appointments() { }

    public Appointments(AppointmentID appointmentID, @Size(max = 20) String comment) {
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
}
