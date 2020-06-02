package com.Clinic_Accounting_System.servlets.admin.patients;

import com.Clinic_Accounting_System.dao.AppointmentDAO;
import com.Clinic_Accounting_System.dao.PatientDAO;
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

@Log4j2
@WebServlet(name = "AdminsDeletePatientServlet", urlPatterns = "/admin/deletePatient")
public class DeletePatientServlet extends HttpServlet {

    private final PatientDAO patientDAO = PatientDAO.getInstance();
    private final AppointmentDAO appointmentDAO = AppointmentDAO.getInstance();

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // fetching patient from db by patientID from request
            Long patientID = Long.parseLong(request.getParameter("patientID"));
            Patient patient = patientDAO.getPatientById(patientID);
            if(patient != null){
                // remove appointments of removed patient
                appointmentDAO.removeAllAppointmentsByPatientId(patientID);
                // remove from `user_info` table, method also removes patient from `users`
                patientDAO.removePatient(patientID);
                // notifying about successful delete operation
                HttpSession session = request.getSession();
                ControllerUtils.giveTicketToMyMessage(session, "Patient successfully deleted!");
                response.sendRedirect("/admin/patients");
            } else {
                HttpSession session = request.getSession();
                ControllerUtils.giveTicketToMyMessage(session, "Such patient do not exist anymore!");
                response.sendRedirect("/admin/patients");
            }
        } catch (SQLException e) {
            log.error("500: SQLException at admin/appointments/AppointmentsPageServlet");
            response.sendRedirect("/errors/500.html");
        }
    }

}
