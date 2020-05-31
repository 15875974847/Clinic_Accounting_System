package com.Clinic_Accounting_System.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class Event {
    private int id;
    private String header;
    private String content;
    private Date startDate;
    private Date endDate;
    private boolean onlyForPersonal;
}
