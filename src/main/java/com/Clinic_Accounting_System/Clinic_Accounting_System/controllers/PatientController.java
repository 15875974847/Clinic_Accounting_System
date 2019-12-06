package com.Clinic_Accounting_System.Clinic_Accounting_System.controllers;


import com.Clinic_Accounting_System.Clinic_Accounting_System.models.Roles;
import com.Clinic_Accounting_System.Clinic_Accounting_System.models.UserInfo;
import com.Clinic_Accounting_System.Clinic_Accounting_System.repositories.DoctorsRepository;
import com.Clinic_Accounting_System.Clinic_Accounting_System.repositories.EventsRepository;
import com.Clinic_Accounting_System.Clinic_Accounting_System.repositories.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private UserInfoRepository patientInfo;

    @Autowired
    private DoctorsRepository doctors;

    @Autowired
    private EventsRepository events;


    @GetMapping (value = "/")
    public String redirectToHomePage() {
        return "redirect:/patient/home";
    }

    @GetMapping (value = "/home")
    public String showHomePage(HttpServletRequest request){
        if(checkPatientAuth(request)){

            return "patient/home";
        } else {
            return "redirect:/sign_in";
        }
    }

    @GetMapping (value = "/account")
    public String showAccountPage(HttpServletRequest request){
        if(checkPatientAuth(request)){

            return "patient/account";
        } else {
            return "redirect:/sign_in";
        }
    }

    @GetMapping (value = "/doctors")
    public String showDoctorsPage(HttpServletRequest request){
        if(checkPatientAuth(request)){

            return "patient/doctors";
        } else {
            return "redirect:/sign_in";
        }
    }

    @GetMapping (value = "/appointments")
    public String showAppointmentsPage(HttpServletRequest request){
        if(checkPatientAuth(request)){

            return "patient/appointments";
        } else {
            return "redirect:/sign_in";
        }
    }


    private boolean checkPatientAuth(HttpServletRequest request){
        /*
            Checking session and Auth attributes for existence,
             also check if user have corresponding role to display this pack of pages.
             If he is not --> go to sign_in controller, it will handle you properly;)
         */
        HttpSession session = request.getSession(false);
        if(session != null) {
            Long id = (Long)session.getAttribute("user_id");
            Roles role = (Roles)session.getAttribute("role");
            if(id != null && role != null){
                return role == Roles.user;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
