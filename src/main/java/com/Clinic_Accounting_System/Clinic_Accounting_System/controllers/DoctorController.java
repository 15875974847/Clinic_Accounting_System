package com.Clinic_Accounting_System.Clinic_Accounting_System.controllers;


import com.Clinic_Accounting_System.Clinic_Accounting_System.models.*;
import com.Clinic_Accounting_System.Clinic_Accounting_System.repositories.*;
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
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private UsersRepository accountInfoService;

    @Autowired
    private UserInfoRepository patientInfoService;

    @Autowired
    private DoctorsRepository doctorsService;

    @Autowired
    private EventsRepository eventsService;

    @Autowired
    private AppointmentsRepository appointmentsService;


    @GetMapping (value = "/")
    public String redirectToHomePage() {
        return "redirect:/doctor/home";
    }

    @GetMapping (value = "/home")
    public String showHomePage(HttpServletRequest request){
        if(checkDocAuth(request)){
            // making some calls to db to fetch necessary info
            HttpSession session = request.getSession();
            // look for name to greet user
            Optional<UserInfo> patientInfo = patientInfoService.findById((Long)session.getAttribute("user_id"));
            if(patientInfo.isPresent()){
                // setting firstname to greet user
                request.setAttribute("user", patientInfo.get().getFirstName());
                // search events for doctor
                ArrayList<Events> events = eventsService.findAllByOnlyForPersonal(true);
                request.setAttribute("events", events);
                return "doctor/home";
            } else {
                return ControllerUtils.processNonexistentUserWithValidSessionParams(session, request);
            }
        } else {
            return "redirect:/sign_in";
        }
    }
    @PostMapping (value = "/addNewEvent")
    public String addNewEvent(HttpServletRequest request){
        if(checkDocAuth(request)){
            HttpSession session = request.getSession();
            // gettin' params from request
            String header = request.getParameter("header");
            String content = request.getParameter("content");
            Date startDate = java.sql.Date.valueOf(request.getParameter("start_date"));
            Date endDate = java.sql.Date.valueOf(request.getParameter("end_date"));
            boolean onlyForPersonal = request.getParameterValues("only_for_personal") != null;
            // creating object with invoked params
            Events event = new Events(header, content, startDate, endDate, onlyForPersonal);
            // and finally save object into db and flush
            eventsService.saveAndFlush(event);
            return "redirect:/doctor/home";
        } else {
            return "redirect:/sign_in";
        }
    }


    private boolean checkDocAuth(HttpServletRequest request){
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
                if(role == Roles.doctor){
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
