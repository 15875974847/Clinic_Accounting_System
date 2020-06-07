package com.Clinic_Accounting_System.servlets.admin.patients;

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
@WebServlet(name = "AdminsPatientsPageServlet", urlPatterns = "/admin/patients")
public class PatientsPageServlet extends HttpServlet {

    private PatientDAO patientDAO = PatientDAO.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // fetch patients from database
            List<Patient> patients = patientDAO.getAll();
            // sett this list of patients as request attrib
            request.setAttribute("patients", patients);
            HttpSession session = request.getSession();
            ControllerUtils.goThru_MessageByTicket_System(session);
            request.getRequestDispatcher("/pages/admin/patients.jsp").forward(request, response);
        } catch (SQLException e) {
            log.error("500: SQLException at admin/patients/PatientsPageServlet: " + e.getMessage());
            request.getRequestDispatcher("/pages/errors/500.jsp").forward(request, response);
        }
    }

}
