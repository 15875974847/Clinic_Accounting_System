package com.Clinic_Accounting_System.servlets.doctor.my_appointments;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "DoctorsCloseAppointmentServlet", urlPatterns = "/doctor/closeAppointmentAndLeaveANote")
public class CloseAppointmentServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
        response.sendRedirect("/doctor/my_appointments");
    }

}