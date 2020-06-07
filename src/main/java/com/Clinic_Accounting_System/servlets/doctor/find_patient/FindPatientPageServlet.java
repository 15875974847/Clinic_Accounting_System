package com.Clinic_Accounting_System.servlets.doctor.find_patient;

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
import java.util.List;

@Log4j2
@WebServlet(name = "DoctorsFindPatientPageServlet", urlPatterns = "/doctor/find_patient")
public class FindPatientPageServlet extends HttpServlet {

    private final PatientDAO patientDAO = PatientDAO.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            // fetch list of patients from database
            List<Patient> patients = patientDAO.getAll();
            // push patients to request
            request.setAttribute("patients", patients);
            // go thru message-by-ticket to display messages
            ControllerUtils.goThru_MessageByTicket_System(session);
            request.getRequestDispatcher("/pages/doctor/find_patient.jsp").forward(request, response);
        } catch(SQLException e) {
            log.error("500: SQLException at doctor/find_patient/FindPatientPageServlet: " + e.getMessage());
            request.getRequestDispatcher("/pages/errors/500.jsp").forward(request, response);
        }
    }

}