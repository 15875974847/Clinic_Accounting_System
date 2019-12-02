package com.Clinic_Accounting_System.Clinic_Accounting_System.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class AppointmentID implements Serializable {

    @NotNull
    @Column(name = "patient_id")
    private Long patientID;

    @NotNull
    @Column(name = "doctor_id")
    private Long doctorID;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date utilDate;

    public AppointmentID() {}

    public AppointmentID(@NotNull Long patientID, @NotNull Long doctorID, @NotNull Date utilDate) {
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.utilDate = utilDate;
    }

    public Long getPatientID() {
        return patientID;
    }

    public void setPatientID(Long patientID) {
        this.patientID = patientID;
    }

    public Long getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(Long doctorID) {
        this.doctorID = doctorID;
    }

    public Date getUtilDate() {
        return utilDate;
    }

    public void setUtilDate(Date utilDate) {
        this.utilDate = utilDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppointmentID)) return false;
        AppointmentID that = (AppointmentID) o;
        return Objects.equals(getDoctorID(), that.getDoctorID()) &&
                Objects.equals(getPatientID(), that.getPatientID()) &&
                        Objects.equals(getUtilDate(), that.getUtilDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDoctorID(), getPatientID(), getUtilDate());
    }

}
