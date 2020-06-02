package com.Clinic_Accounting_System.servlets.patient.my_appointments;

import com.Clinic_Accounting_System.dao.AppointmentDAO;
import com.Clinic_Accounting_System.dao.PatientDAO;
import com.Clinic_Accounting_System.entities.Appointment;
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
import java.sql.SQLException;
import java.util.List;

@Log4j2
@WebServlet(name = "PatientsMyAppointmentsPageServlet", urlPatterns = "/patient/my_appointments")
public class MyAppointmentsPageServlet extends HttpServlet {

    private final PatientDAO patientDAO = PatientDAO.getInstance();
    private final AppointmentDAO appointmentDAO = AppointmentDAO.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            HttpSession session = request.getSession();
            // fetch patient from db
            Patient patient = patientDAO.getPatientById((Long)session.getAttribute("user_id"));
            if(patient != null){
                // fetch patient's appointments from db
                List<Appointment> appointments = appointmentDAO.getAppointmentsByPatientId(patient.getId());
                request.setAttribute("appointments", appointments);
                request.getRequestDispatcher("patient/my_appointments.jsp").forward(request, response);
            } else {
                ControllerUtils.processNonexistentUserWithValidSessionParams(session, request, response);
            }
        } catch (SQLException e) {
            log.error("500: SQLException at patient/my_appointments/MyAppointmentsPageServlet");
            request.getRequestDispatcher("errors/500.html").forward(request, response);
        }
    }

}
