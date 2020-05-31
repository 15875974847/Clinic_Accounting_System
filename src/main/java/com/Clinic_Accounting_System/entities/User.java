package com.Clinic_Accounting_System.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private int id;
    private String username;
    private String password;
    private String role;
}
