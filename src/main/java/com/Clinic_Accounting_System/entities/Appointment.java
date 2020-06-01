package com.Clinic_Accounting_System.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class Appointment {
    private long doctorId;
    private long patientId;
    private Date date;
    private int numberInQueue;
    private String comment;
    // Foreign keys
    private Doctor doctor;
    private Patient patient;
}
