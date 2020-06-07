package com.Clinic_Accounting_System.servlets.patient.home;

import com.Clinic_Accounting_System.dao.EventDAO;
import com.Clinic_Accounting_System.dao.PatientDAO;
import com.Clinic_Accounting_System.entities.Event;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@WebServlet(name = "PatientsHomePageServlet", urlPatterns = "/patient/home")
public class HomePageServlet extends HttpServlet {

    private final PatientDAO patientDAO = PatientDAO.getInstance();
    private final EventDAO eventDAO = EventDAO.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            HttpSession session = request.getSession();
            // fetch patient's info
            final Patient patient = patientDAO.getPatientById((Long)session.getAttribute("user_id"));
            if(patient != null){
                // user attrib to greet user by first name
                request.setAttribute("user", patient.getFirstname());
                // fetch patient's events
                List<Event> events = eventDAO.getAllEventsByOnlyForPersonal(false);
                request.setAttribute("events", events);
                request.getRequestDispatcher("/pages/patient/home.jsp").forward(request, response);
            } else {
                ControllerUtils.processNonexistentUserWithValidSessionParams(session, request, response);
            }
        } catch(SQLException e) {
            log.error("500: SQLException at patient/home/HomePageServlet: " + e.getMessage());
            request.getRequestDispatcher("/pages/errors/500.jsp").forward(request, response);
        }
    }

}