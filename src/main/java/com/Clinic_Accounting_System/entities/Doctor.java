package com.Clinic_Accounting_System.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Doctor {
    private long id;
    private String degree;
    private String specialization;
    // Foreign key
    private Staff staff;
}
