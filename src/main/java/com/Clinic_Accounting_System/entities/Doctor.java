package com.Clinic_Accounting_System.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Doctor {
    private Staff id;
    private String degree;
    private String specialization;
}
