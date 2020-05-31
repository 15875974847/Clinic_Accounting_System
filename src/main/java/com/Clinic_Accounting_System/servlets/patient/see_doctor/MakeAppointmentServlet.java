package com.Clinic_Accounting_System.servlets.patient.see_doctor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "PatientsMakeAppointmentServlet", urlPatterns = "/patient/makeAppointment")
public class MakeAppointmentServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
            response.sendRedirect("/patient/see_doctor");
        } else {
            return ControllerUtils.processNonexistentUserWithValidSessionParams(session, request);
        }
    }

}
