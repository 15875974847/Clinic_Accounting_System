package com.Clinic_Accounting_System.Clinic_Accounting_System.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/patient")
public class PatientController {

    @GetMapping("/")
    public String displayHomePage(){
        return "patient/home";
    }

}
