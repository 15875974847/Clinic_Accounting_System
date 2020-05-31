package com.Clinic_Accounting_System.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class Appointment {
    Doctor doctorId;
    Patient patientId;
    Date date;
    int numberInQueue;
    String comment;
}
