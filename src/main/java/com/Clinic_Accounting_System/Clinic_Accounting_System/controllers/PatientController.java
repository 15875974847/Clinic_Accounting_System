package com.Clinic_Accounting_System.Clinic_Accounting_System.controllers;


import com.Clinic_Accounting_System.Clinic_Accounting_System.models.Events;
import com.Clinic_Accounting_System.Clinic_Accounting_System.models.Roles;
import com.Clinic_Accounting_System.Clinic_Accounting_System.models.UserInfo;
import com.Clinic_Accounting_System.Clinic_Accounting_System.models.Users;
import com.Clinic_Accounting_System.Clinic_Accounting_System.repositories.DoctorsRepository;
import com.Clinic_Accounting_System.Clinic_Accounting_System.repositories.EventsRepository;
import com.Clinic_Accounting_System.Clinic_Accounting_System.repositories.UserInfoRepository;
import com.Clinic_Accounting_System.Clinic_Accounting_System.repositories.UsersRepository;
import com.Clinic_Accounting_System.Clinic_Accounting_System.utils.AppLogger;
import com.Clinic_Accounting_System.Clinic_Accounting_System.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private UsersRepository accountInfoService;

    @Autowired
    private UserInfoRepository patientInfoService;

    @Autowired
    private DoctorsRepository doctorsService;

    @Autowired
    private EventsRepository eventsService;


    @GetMapping (value = "/")
    public String redirectToHomePage() {
        return "redirect:/patient/home";
    }

    @GetMapping (value = "/home")
    public String showHomePage(HttpServletRequest request){
        if(checkPatientAuth(request)){
            // making some calls to db to fetch necessary info
            HttpSession session = request.getSession();
            // look for name to greet user
            Optional<UserInfo> patientInfo = patientInfoService.findById((Long)session.getAttribute("user_id"));
            if(patientInfo.isPresent()){
                // setting firstname as request attribute to loose it after redirection
                request.setAttribute("user", patientInfo.get().getFirstName());
                // search events for patient
                ArrayList<Events> events = eventsService.findAllByOnlyForPersonal(false);
                request.setAttribute("events", events);

                return "patient/home";
            } else {
                return ControllerUtils.processNonexistentUserWithValidSessionParams(session, request);
            }
        } else {
            return "redirect:/sign_in";
        }
    }


    @GetMapping (value = "/account")
    public String showAccountPage(HttpServletRequest request){
        if(checkPatientAuth(request)){
            // making some calls to retrieve account info from database
            HttpSession session = request.getSession();
            Optional<Users> accountInfo = accountInfoService.findById((Long)session.getAttribute("user_id"));
            if(accountInfo.isPresent()){
                // setting request params, so jsp can render the page
                request.setAttribute("username", accountInfo.get().getUsername());
                request.setAttribute("password", accountInfo.get().getPassword());

                return "patient/account";
            } else {
                return ControllerUtils.processNonexistentUserWithValidSessionParams(session, request);
            }
        } else {
            return "redirect:/sign_in";
        }
    }

    // invoked by form when clicking to Edit Account Info
    @PostMapping(value = "/editAccountInfo")
    public String editAccountInfo(HttpServletRequest request){
        if(checkPatientAuth(request)){
            String oldUsername = request.getParameter("oldUsername");
            String oldPassword = request.getParameter("oldPassword");
            String newUsername = request.getParameter("newUsername");
            String newPassword = request.getParameter("newPassword");

            HttpSession session = request.getSession();
            if(!oldUsername.equals(newUsername) || !oldPassword.equals(newPassword)){
                // then making some calls to retrieve user account info from database
                // this method returns a reference to the entity with the given identifier, ie we JPA will execute SQL update statement on save method
                Users accountInfo = accountInfoService.getOne((Long)session.getAttribute("user_id"));
                if(accountInfo != null){
                    // resetting username and password parameters
                    accountInfo.setUsername(newUsername);
                    accountInfo.setPassword(newPassword);
                    accountInfoService.saveAndFlush(accountInfo);
                    return "redirect:/patient/account";
                } else {
                    return ControllerUtils.processNonexistentUserWithValidSessionParams(session, request);
                }
            } else {
                // if user entered same params redirect at the same page and display an error message
                ControllerUtils.giveTicketToMyMessage(session, "U entered same params!");
                return "redirect:/patient/account";
            }
        } else {
            return "redirect:/sign_in";
        }
    }


    @GetMapping (value = "/pers_info")
    public String showAccountPersonalInfo(HttpServletRequest request){
        if(checkPatientAuth(request)){
            // making call to patient service repo to retrieve user info from database
            HttpSession session = request.getSession();
            Optional<UserInfo> patientInfo = patientInfoService.findById((Long)session.getAttribute("user_id"));
            if(patientInfo.isPresent()){
                // setting request params, so jsp can render the page
                request.setAttribute("firstname", patientInfo.get().getFirstName());
                request.setAttribute("midname", patientInfo.get().getMiddleName());
                request.setAttribute("lastname", patientInfo.get().getLastName());
                request.setAttribute("email", patientInfo.get().getEmail());
                request.setAttribute("phone", patientInfo.get().getPhone());
                request.setAttribute("dob", patientInfo.get().getDateOfBirth().toString());
                request.setAttribute("address", patientInfo.get().getAddress());
                request.setAttribute("medHistory", patientInfo.get().getMedicalHistory());
                return "patient/pers_info";
            } else {
                return ControllerUtils.processNonexistentUserWithValidSessionParams(session, request);
            }
        } else {
            return "redirect:/sign_in";
        }
    }
    // invoked by form when clicking to Edit Personal Info
    @PostMapping(value = "/editPersonalInfo")
    public String editPersonalInfo(HttpServletRequest request){
        if(checkPatientAuth(request)){
            HttpSession session = request.getSession();
            // making call to db to get reference to patient info object
            UserInfo patientInfo = patientInfoService.getOne((Long)session.getAttribute("user_id"));
            if(patientInfo != null){
                // gettin' params from request
                String firstname = request.getParameter("firstname");
                String midname = request.getParameter("midname");
                String lastname = request.getParameter("lastname");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                String dob = request.getParameter("dob");
                String address = request.getParameter("address");
                // setting updated patient info params
                patientInfo.setFirstName(firstname);
                patientInfo.setMiddleName(midname);
                patientInfo.setLastName(lastname);
                patientInfo.setEmail(email);
                patientInfo.setPhone(phone);
                patientInfo.setDateOfBirth(java.sql.Date.valueOf(dob));     // important: dob should be yyyy-mm-dd(american format)
                patientInfo.setAddress(address);
                // and apply changes to db
                patientInfoService.saveAndFlush(patientInfo);
                return "redirect:/patient/pers_info";
            } else {
                return ControllerUtils.processNonexistentUserWithValidSessionParams(session, request);
            }
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
                if(role == Roles.user){
                    // updating session interval
                    session.setMaxInactiveInterval(30*60);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


}
