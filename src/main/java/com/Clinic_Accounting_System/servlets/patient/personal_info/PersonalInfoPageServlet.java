package com.Clinic_Accounting_System.servlets.patient.personal_info;

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
import java.util.Optional;

@Log4j2
@WebServlet(name = "PatientsPersonalInfoPageServlet", urlPatterns = "/patient/pers_info")
public class PersonalInfoPageServlet extends HttpServlet {

    private final PatientDAO patientDAO = PatientDAO.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            HttpSession session = request.getSession();
            // fetch patient from db
            Patient patient = patientDAO.getPatientById((Long)session.getAttribute("user_id"));
            if(patient != null){
                // set request attribs, so jsp can render the page
                request.setAttribute("firstname", patient.getFirstname());
                request.setAttribute("midname", patient.getMiddlename());
                request.setAttribute("lastname", patient.getLastname());
                request.setAttribute("email", patient.getEmail());
                request.setAttribute("phone", patient.getPhone());
                request.setAttribute("dob", patient.getDob());
                request.setAttribute("address", patient.getAddress());
                request.setAttribute("medHistory", patient.getMedicalHistory());
                // go thru message-by-ticket system
                ControllerUtils.goThru_MessageByTicket_System(session);
                request.getRequestDispatcher("patient/pers_info.jsp").forward(request, response);
            } else {
                ControllerUtils.processNonexistentUserWithValidSessionParams(session, request, response);
            }
        } catch (SQLException e) {
            log.error("500: SQLException at patient/personal_info/PersonalInfoPageServlet");
            request.getRequestDispatcher("errors/500.html").forward(request, response);
        }
    }

}
