package com.Clinic_Accounting_System.Clinic_Accounting_System.controllers;

import com.Clinic_Accounting_System.Clinic_Accounting_System.models.*;
import com.Clinic_Accounting_System.Clinic_Accounting_System.repositories.*;
import com.Clinic_Accounting_System.Clinic_Accounting_System.utils.ControllerUtils;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsersRepository usersService;

    @Autowired
    private UserInfoRepository userInfoService;

    @Autowired
    private StaffEntityRepository staffEntityService;

    @Autowired
    private DoctorsRepository doctorsService;

    @Autowired
    private EventsRepository eventsService;

    @Autowired
    private AppointmentsRepository appointmentsService;

    @GetMapping (value = "/")
    public String redirectToHomePage() {
        return "redirect:/admin/home";
    }

    @GetMapping (value = "/home")
    public String showPage(HttpServletRequest request) {
        if(checkAdminAuth(request)){
            return "admin/home";
        } else {
            return "redirect:/sign_in";
        }
    }

    @GetMapping (value = "/events")
    public String showEventsPage(HttpServletRequest request){
        if(checkAdminAuth(request)){
            // making call to db to fetch all events
            List<Events> events = eventsService.findAll();
            request.setAttribute("events", events);
            // process thru message-by-ticket system
            HttpSession session = request.getSession();
            ControllerUtils.goThru_MessageByTicket_System(session);
            return "admin/events";
        } else{
            return "redirect:/sign_in";
        }
    }

    @PostMapping(value = "/addNewEvent")
    public String addNewEvent(HttpServletRequest request){
        if(checkAdminAuth(request)){
            HttpSession session = request.getSession();
            // gettin' params from request
            String header = request.getParameter("header");
            String content = request.getParameter("content");
            Date startDate = java.sql.Date.valueOf(request.getParameter("start_date"));
            ControllerUtils.makeCorrectionForTimeZone(startDate);
            Date endDate = java.sql.Date.valueOf(request.getParameter("end_date"));
            ControllerUtils.makeCorrectionForTimeZone(endDate);
            boolean onlyForPersonal = request.getParameterValues("only_for_personal") != null;
            // creating object with invoked params
            Events event = new Events(header, content, startDate, endDate, onlyForPersonal);
            // and finally save object into db and flush
            eventsService.saveAndFlush(event);
            // give ticket to message notifying user that event successfully created
            ControllerUtils.giveTicketToMyMessage(session, "Event successfully created!");
            return "redirect:/admin/events";
        } else {
            return "redirect:/sign_in";
        }
    }

    @PostMapping(value="/deleteEvent")
    public String deleteEvent(HttpServletRequest request){
        if(checkAdminAuth(request)){
            // gettin' eventID param from request
            Long eventID = Long.parseLong(request.getParameter("eventID"));
            // removing it from database
            eventsService.deleteById(eventID);
            // notifying about successful delete operation
            HttpSession session = request.getSession();
            ControllerUtils.giveTicketToMyMessage(session, "Event successfully deleted!");
            return "redirect:/admin/events";
        } else {
            return "redirect:/sign_in";
        }
    }


    @GetMapping (value = "/doctors")
    public String showDoctorsPage(HttpServletRequest request){
        if(checkAdminAuth(request)){
            // making call to db to fetch all doctors
            List<Doctors> doctors = doctorsService.findAll();
            request.setAttribute("doctors", doctors);
            // process thru message-by-ticket system
            HttpSession session = request.getSession();
            ControllerUtils.goThru_MessageByTicket_System(session);
            return "admin/doctors";
        } else{
            return "redirect:/sign_in";
        }
    }

    @PostMapping(value="/addNewDoctor")
    public String addNewDoctor(HttpServletRequest request){
        if(checkAdminAuth(request)){
            // scrapping all params from request
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String firstname = request.getParameter("firstname");
            String midname = request.getParameter("midname");
            String lastname = request.getParameter("lastname");
            Date dob = java.sql.Date.valueOf(request.getParameter("dob"));
            ControllerUtils.makeCorrectionForTimeZone(dob);
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String address = request.getParameter("address");
            String salary = request.getParameter("salary");
            String degree = request.getParameter("degree");
            String specialization = request.getParameter("specialization");
            // here supposed to be different checks, but i don't have time, so let's omit them
            // persisting data into database
            if(!usersService.existsByUsername(username)) {
                Users user = new Users(username, password, Roles.doctor);
                usersService.saveAndFlush(user);
                UserInfo userInfo = new UserInfo(firstname, midname, lastname,
                        dob, phone, email, address, user);
                userInfoService.saveAndFlush(userInfo);
                StaffEntity staffEntity = new StaffEntity(Double.parseDouble(salary), userInfo);
                staffEntityService.saveAndFlush(staffEntity);
                Doctors doctor = new Doctors(degree, specialization, staffEntity);
                doctorsService.saveAndFlush(doctor);
                // notifying administrator about successful add operation
                HttpSession session = request.getSession();
                ControllerUtils.giveTicketToMyMessage(session, "Doctor successfully added!");
                return "redirect:/admin/doctors";
            } else {
                HttpSession session = request.getSession();
                ControllerUtils.giveTicketToMyMessage(session, "Doctor with such username already exists!");
                return "redirect:/admin/doctors";
            }
        } else {
            return "redirect:/sign_in";
        }
    }

    @PostMapping(value="/deleteDoctor")
    public String deleteDoctor(HttpServletRequest request){
        if(checkAdminAuth(request)){
            // gettin' docID param from request
            Long docID = Long.parseLong(request.getParameter("docID"));
            // if you want to know why i'm doing this -> go see AppointmentID gotcha
            appointmentsService.removeAllByAppointmentID_Doctor_Id(docID);
            // removing doc from database, cascade will do the rest with other doctor's entity dependencies
            doctorsService.deleteById(docID);
            // notifying about successful delete operation
            HttpSession session = request.getSession();
            ControllerUtils.giveTicketToMyMessage(session, "Doctor successfully deleted!");
            return "redirect:/admin/doctors";
        } else {
            return "redirect:/sign_in";
        }
    }


    @GetMapping(value = "/patients")
    public String showPatients(HttpServletRequest request){
        if(checkAdminAuth(request)){
            // fetching patients from database
            List<UserInfo> patients = userInfoService.findAll();
            // setting this list of patients as request attrib
            request.setAttribute("patients", patients);
            HttpSession session = request.getSession();
            ControllerUtils.goThru_MessageByTicket_System(session);
            return "admin/patients";
        } else {
            return "redirect:/sign_in";
        }
    }

    @PostMapping(value="/editPatient")
    public String editPatient(HttpServletRequest request){
        if(checkAdminAuth(request)){
            // fetching params from request
            Long patientID = Long.parseLong(request.getParameter("patientID"));
            // checking if such patient exists
            Users user = usersService.getOne(patientID);
            if(user != null){
                // continue fetching
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String firstname = request.getParameter("firstname");
                String midname = request.getParameter("midname");
                String lastname = request.getParameter("lastname");
                Date dob = java.sql.Date.valueOf(request.getParameter("dob"));
                ControllerUtils.makeCorrectionForTimeZone(dob);
                String phone = request.getParameter("phone");
                String email = request.getParameter("email");
                String address = request.getParameter("address");
                // update data in Users
                user.setUsername(username);
                user.setPassword(password);
                usersService.saveAndFlush(user);
                // update data in UserInfo
                UserInfo userInfo = userInfoService.getOne(patientID);
                userInfo.setFirstName(firstname);
                userInfo.setMiddleName(midname);
                userInfo.setLastName(lastname);
                userInfo.setDateOfBirth(dob);
                userInfo.setPhone(phone);
                userInfo.setEmail(email);
                userInfo.setAddress(address);
                userInfoService.saveAndFlush(userInfo);
                // give admin notification about successful update operation
                HttpSession session = request.getSession();
                ControllerUtils.giveTicketToMyMessage(session, "Patient's info successfully updated!");
                return "redirect:/admin/patients";
            } else {
                HttpSession session = request.getSession();
                ControllerUtils.giveTicketToMyMessage(session, "User with such id don't exist anymore");
                return "redirect:/admin/patients";
            }
        } else {
            return "redirect:/sign_in";
        }
    }

    @PostMapping(value="/deletePatient")
    public String deletePatient(HttpServletRequest request){
        if(checkAdminAuth(request)){
            // fetching patientID from request
            Long patientID = Long.parseLong(request.getParameter("patientID"));
            UserInfo patient = userInfoService.getOne(patientID);
            if(patient != null){
                // if you want to know why i'm doing this -> go see AppointmentID gotcha
                appointmentsService.removeAllByAppointmentID_Patient_Id(patientID);
                // deleting from UserInfo table, cascade will delete from Users
                userInfoService.deleteById(patientID);
                // notifying about successful delete operation
                HttpSession session = request.getSession();
                ControllerUtils.giveTicketToMyMessage(session, "Patient successfully deleted!");
                return "redirect:/admin/patients";
            } else {
                HttpSession session = request.getSession();
                ControllerUtils.giveTicketToMyMessage(session, "Such patient do not exist anymore!");
                return "redirect:/admin/patients";
            }
        } else {
            return "redirect:/sign_in";
        }
    }


    @GetMapping(value="/appointments")
    public String showAppointments(HttpServletRequest request){
        if(checkAdminAuth(request)){
            // fetching from database all appointments
            List<Appointments> appointments = appointmentsService.findAll();
            // push them into request scope, yeah, i know it's bad idea, but i don't have time to implement something better
            request.setAttribute("appointments", appointments);
            HttpSession session = request.getSession();
            ControllerUtils.goThru_MessageByTicket_System(session);
            return "admin/appointments";
        } else {
            return "redirect:/sign_in";
        }
    }

    @PostMapping(value="/deleteAllAppointmentsAfterDate")
    public String deleteAllAppointmentsAfterDate(HttpServletRequest request){
        if(checkAdminAuth(request)){
            // fetching date from request
            Date date = java.sql.Date.valueOf(request.getParameter("date"));
            ControllerUtils.makeCorrectionForTimeZone(date);
            // delete all appointments after date
            int numberOfDeletedAppointments = appointmentsService.removeOlderThan(date);
            // give ticket to success message
            HttpSession session = request.getSession();
            ControllerUtils.giveTicketToMyMessage(session, numberOfDeletedAppointments + " appointments successfully deleted!");
            return "redirect:/admin/appointments";
        } else {
            return "redirect:/sign_in";
        }
    }


    private boolean checkAdminAuth(HttpServletRequest request){
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
                if(role == Roles.admin){
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
