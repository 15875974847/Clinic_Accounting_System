package com.Clinic_Accounting_System.Clinic_Accounting_System.controllers;


import com.Clinic_Accounting_System.Clinic_Accounting_System.models.*;
import com.Clinic_Accounting_System.Clinic_Accounting_System.repositories.*;
import com.Clinic_Accounting_System.Clinic_Accounting_System.utils.AppLogger;
import com.Clinic_Accounting_System.Clinic_Accounting_System.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
                List<Events> events = eventsService.findAll();
                request.setAttribute("events", events);
                // process thru message-by-ticket system
                ControllerUtils.goThru_MessageByTicket_System(session);
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
            return "redirect:/doctor/home";
        } else {
            return "redirect:/sign_in";
        }
    }


    @GetMapping (value = "/account")
    public String showAccountPage(HttpServletRequest request){
        if(checkDocAuth(request)){
            // making some calls to retrieve account info from database
            HttpSession session = request.getSession();
            Optional<Users> accountInfo = accountInfoService.findById((Long)session.getAttribute("user_id"));
            if(accountInfo.isPresent()){
                // setting request params, so jsp can render the page
                request.setAttribute("username", accountInfo.get().getUsername());
                request.setAttribute("password", accountInfo.get().getPassword());
                // go thought message-by-ticket system
                ControllerUtils.goThru_MessageByTicket_System(session);
                return "doctor/account";
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
        if(checkDocAuth(request)){
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
                    // give ticket to successful username and password update message
                    ControllerUtils.giveTicketToMyMessage(session, "Username and password updated!");
                    return "redirect:/doctor/account";
                } else {
                    return ControllerUtils.processNonexistentUserWithValidSessionParams(session, request);
                }
            } else {
                // if user entered same params redirect at the same page and display an error message
                ControllerUtils.giveTicketToMyMessage(session, "U entered same params!");
                return "redirect:/doctor/account";
            }
        } else {
            return "redirect:/sign_in";
        }
    }


    @GetMapping (value = "/pers_info")
    public String showAccountPersonalInfo(HttpServletRequest request){
        if(checkDocAuth(request)){
            // making call to patient service repo to retrieve doctor info from database
            HttpSession session = request.getSession();
            Optional<Doctors> doctorInfo = doctorsService.findById((Long)session.getAttribute("user_id"));
            if(doctorInfo.isPresent()){
                // setting doctor obj as request parameter
                request.setAttribute("doctor", doctorInfo.get());
                // go thought message-by-ticket system
                ControllerUtils.goThru_MessageByTicket_System(session);
                return "doctor/pers_info";
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
        if(checkDocAuth(request)){
            HttpSession session = request.getSession();
            // making call to db to get reference to doctor info object
            Doctors doctorInfo = doctorsService.getOne((Long)session.getAttribute("user_id"));
            if(doctorInfo != null){
                // retrieving parameters from request
                String firstname = request.getParameter("newFirstname");
                String midname = request.getParameter("newMidname");
                String lastname = request.getParameter("newLastname");
                String email = request.getParameter("newEmail");
                String phone = request.getParameter("newPhone");
                Date dob = java.sql.Date.valueOf(request.getParameter("newDOB"));
                ControllerUtils.makeCorrectionForTimeZone(dob);
                String address = request.getParameter("newAddress");
                String specialization = request.getParameter("newSpecialization");
                String degree = request.getParameter("newDegree");
                // setting updated doctor's info params
                doctorInfo.getStaffEntity().getUserInfo().setFirstName(firstname);
                doctorInfo.getStaffEntity().getUserInfo().setMiddleName(midname);
                doctorInfo.getStaffEntity().getUserInfo().setLastName(lastname);
                doctorInfo.getStaffEntity().getUserInfo().setEmail(email);
                doctorInfo.getStaffEntity().getUserInfo().setPhone(phone);
                doctorInfo.getStaffEntity().getUserInfo().setDateOfBirth(dob);
                doctorInfo.getStaffEntity().getUserInfo().setAddress(address);
                doctorInfo.setSpecialization(specialization);
                doctorInfo.setDegree(degree);
                // and flush changes to db
                doctorsService.saveAndFlush(doctorInfo);
                // give ticket to message about successful update
                ControllerUtils.giveTicketToMyMessage(session, "Personal information successfully updated!");
                return "redirect:/doctor/pers_info";
            } else {
                return ControllerUtils.processNonexistentUserWithValidSessionParams(session, request);
            }
        } else {
            return "redirect:/sign_in";
        }
    }


    @GetMapping (value = "/see_doctor")
    public String show_SeeDoctor_Page(HttpServletRequest request){
        if(checkDocAuth(request)){
            HttpSession session = request.getSession();
            // making some calls to retrieve info from database
            List<Doctors> doctors = doctorsService.findAll();
            // setting list of doctors as request attribute
            request.setAttribute("doctors", doctors);
            // pass this page thru message-only-with-ticket system
            ControllerUtils.goThru_MessageByTicket_System(session);
            return "doctor/see_doctor";
        } else {
            return "redirect:/sign_in";
        }
    }
    // this post request is used to register patient appointment in database
    @PostMapping (value = "/makeAppointment")
    public String makeAnAppointment(HttpServletRequest request){
        if(checkDocAuth(request)){
            HttpSession session = request.getSession();
            // scrapping params
            Long patientID = (Long)session.getAttribute("user_id");
            Long doctorID = Long.parseLong(request.getParameter("doctorID"));
            Date date = java.sql.Date.valueOf(request.getParameter("date"));
            ControllerUtils.makeCorrectionForTimeZone(date);
            String comment = request.getParameter("comment");
            // making call to database to find out how many appointments already we have on this date
            int numberInQueue = appointmentsService.countAppointmentsByAppointmentID_Doctor_IdAndAppointmentID_Date(doctorID, date).intValue() + 1;

            // taking from db necessary to create new appointment patient and doctor objects
            UserInfo patientInfo = patientInfoService.getOne(patientID);
            Doctors doctorInfo = doctorsService.getOne(doctorID);
            // and check those objects on existence
            if(patientInfo != null && doctorInfo != null){
                // creating new appointment on chosen date
                AppointmentID newAppointmentID = new AppointmentID(patientInfo, doctorInfo, date, numberInQueue);
                Appointments newAppointment = new Appointments(newAppointmentID, comment);
                // and save and flush(always flush after yourself!:))
                appointmentsService.saveAndFlush(newAppointment);
                // add give ticket to message to notify user that appointment to doctor successfully created
                ControllerUtils.giveTicketToMyMessage(session, "Appointment to selected doctor successfully created!");
                return "redirect:/doctor/see_doctor";
            } else {
                return ControllerUtils.processNonexistentUserWithValidSessionParams(session, request);
            }
        } else {
            return "redirect:/sign_in";
        }
    }


    @GetMapping(value = "/find_patient")
    public String findPatient(HttpServletRequest request){
        if(checkDocAuth(request)){
            HttpSession session = request.getSession();
            // making call to db to fetch all patients
            List<UserInfo> patients = patientInfoService.findAll();
            // setting list of patients as a param
            request.setAttribute("patients", patients);
            // go thru message-by-ticket to display messages
            ControllerUtils.goThru_MessageByTicket_System(session);
            return "doctor/find_patient";
        } else{
            return "redirect:/sign_in";
        }
    }

    @PostMapping(value="changePatientsMedicalHistory")
    public String changePatientsMedicalHistory(HttpServletRequest request){
        if(checkDocAuth(request)){
            HttpSession session = request.getSession();
            // retrieving params from request
            Long patientID = Long.parseLong(request.getParameter("patientID"));
            String newMedicalHistory = request.getParameter("newMedicalHistory");
            // making call to database to make LAZY fetch of patient info
            UserInfo patient = patientInfoService.getOne(patientID);
            if(patient != null){
                // update it's medical history
                patient.setMedicalHistory(newMedicalHistory);
                // and flush changes
                patientInfoService.saveAndFlush(patient);
                // give message about successful update
                ControllerUtils.giveTicketToMyMessage(session, "Medical history successfully updated!");
                return "redirect:/doctor/find_patient";
            } else {
                ControllerUtils.giveTicketToMyMessage(session, "Such patient don't exist anymore!");
                return "redirect:/doctor/find_patient";
            }
        } else{
            return "redirect:/sign_in";
        }
    }


    @GetMapping(value="my_appointments")
    public String showAppointments(HttpServletRequest request){
        if(checkDocAuth(request)){
            HttpSession session = request.getSession();
            // fetching from session user_id param
            Long user_id = (Long)session.getAttribute("user_id");
            // fetching appointment's info from database
            List<Appointments> asPatientAppointments = appointmentsService.findAllByAppointmentID_Patient_Id(user_id);
            List<Appointments> asDoctorAppointments = appointmentsService.findAllByAppointmentID_Doctor_Id(user_id);
            // set those two lists as attributes of request scope
            request.setAttribute("asPatientAppointments", asPatientAppointments);
            request.setAttribute("asDoctorAppointments", asDoctorAppointments);
            // go thru message-by-ticket system
            ControllerUtils.goThru_MessageByTicket_System(session);
            return "doctor/my_appointments";
        } else {
            return "redirect:/sign_in";
        }
    }

    @PostMapping(value="/closeAppointmentAndLeaveANote")
    public String closeAppointmentAndLeaveANote(HttpServletRequest request){
        if(checkDocAuth(request)){
            HttpSession session = request.getSession();

            // scrapping needed params from post request scope and session
            Long doctorID = (Long)session.getAttribute("user_id");
            Long patientID = Long.parseLong(request.getParameter("patientID"));
            Date date = java.sql.Date.valueOf(request.getParameter("date"));
            ControllerUtils.makeCorrectionForTimeZone(date);
            int numberInQueue = Integer.parseInt(request.getParameter("numberInQueue"));
            String note = request.getParameter("note");

            // message about status of operation
            String statusMessage;

            // making call to database to check if such patient and doctor still existing there
            UserInfo patient = patientInfoService.getOne(patientID);
            Doctors doctor = doctorsService.getOne(doctorID);
            if(patient != null && doctor != null){
                // deleting Appointment instance by AppointmentID fields
                appointmentsService.deleteByAppointmentID_DoctorAndAppointmentID_PatientAndAppointmentID_NumberInQueue(doctor, patient, numberInQueue);
                // adding note to client's medical history if note exists
                if(!note.equals("")){
                    System.out.println("adding note");
                    patient.setMedicalHistory(patient.getMedicalHistory() + "\n"+ "|" + note + "|");
                    // flushing changes to database
                    patientInfoService.saveAndFlush(patient);
                }
                // giving ticket to message about successful deleting and updating patient's information
                statusMessage = "Appointment successfully closed!";
            } else {
                statusMessage = "Patient and/or doctor do not exist anymore!";
            }
            // giving ticket to statusMessage to display result of operation
            ControllerUtils.giveTicketToMyMessage(session, statusMessage);
            return "redirect:/doctor/my_appointments";
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
