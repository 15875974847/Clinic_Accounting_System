package com.Clinic_Accounting_System.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class Patient {
    private Long id;
    private String firstname;
    private String lastname;
    private String middlename;
    private Date dob;
    private String email;
    private String phone;
    private String address;
    private String medicalHistory;
    // Foreign key
    private User user;
}
