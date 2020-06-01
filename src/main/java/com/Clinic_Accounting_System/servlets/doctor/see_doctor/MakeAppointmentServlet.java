package com.Clinic_Accounting_System.servlets.doctor.see_doctor;

import com.Clinic_Accounting_System.dao.AppointmentDAO;
import com.Clinic_Accounting_System.dao.DoctorDAO;
import com.Clinic_Accounting_System.dao.PatientDAO;
import com.Clinic_Accounting_System.entities.Doctor;
import com.Clinic_Accounting_System.entities.Patient;
import com.Clinic_Accounting_System.utils.ControllerUtils;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

@Log4j2
@WebServlet(name = "DoctorsMakeAnAppointmentServlet", urlPatterns = "/doctor/makeAppointment")
public class MakeAppointmentServlet extends HttpServlet {

    private final AppointmentDAO appointmentDAO = AppointmentDAO.getInstance();
    private final PatientDAO patientDAO = PatientDAO.getInstance();
    private final DoctorDAO doctorDAO = DoctorDAO.getInstance();

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            // scrapping params
            Long patientID = (Long)session.getAttribute("user_id");
            Long doctorID = Long.parseLong(request.getParameter("doctorID"));
            Date date = java.sql.Date.valueOf(request.getParameter("date"));
            ControllerUtils.makeCorrectionForTimeZone(date);
            String comment = request.getParameter("comment");
            // making call to database to find out how many appointments already we have on this date
            int numberInQueue = appointmentDAO.countAppointmentsByDocIdAndDate(doctorID, date) + 1;

            // checking if patient and doc with such ids are exist in database
            Patient patient = patientDAO.getPatientById(patientID);
            Doctor doctor = doctorDAO.getDoctorById(doctorID);
            if(patient != null && doctor != null){
                // creating new appointment on chosen date
                appointmentDAO.createAppointment(doctorID, patientID, numberInQueue, date, comment);
                // add give ticket to message to notify user that appointment to doctor successfully created
                ControllerUtils.giveTicketToMyMessage(session, "Appointment to selected doctor successfully created!");
                response.sendRedirect("/doctor/see_doctor");
            } else {
                ControllerUtils.processNonexistentUserWithValidSessionParams(session, request, response);
            }
        } catch(SQLException e) {
            log.error("500: SQLException at doctor/see_doctor/MakeAppointmentServlet");
            request.getRequestDispatcher("errors/500.html").forward(request, response);
        }
    }

}