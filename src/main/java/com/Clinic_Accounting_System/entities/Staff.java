package com.Clinic_Accounting_System.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Staff {
    private Patient id;
    private double salary;
}
