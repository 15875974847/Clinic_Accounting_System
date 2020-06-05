package com.Clinic_Accounting_System.servlets.doctor.home;

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
import java.util.List;
import java.util.Optional;

@Log4j2
@WebServlet(name = "DoctorsHomePageServlet", urlPatterns = "/doctor/home")
public class HomePageServlet extends HttpServlet {

    private final PatientDAO patientDAO = PatientDAO.getInstance();
    private final EventDAO eventDAO = EventDAO.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // making some calls to db to fetch necessary info
            HttpSession session = request.getSession();
            // look for name to greet user
            Patient patient = patientDAO.getPatientById((Long)session.getAttribute("user_id"));
            if(patient != null){
                // set firstname to greet user
                request.setAttribute("user", patient.getFirstname());
                // search events for doctor
                List<Event> events = eventDAO.getAll();
                request.setAttribute("events", events);
                // go thru message-by-ticket system
                ControllerUtils.goThru_MessageByTicket_System(session);
                request.getRequestDispatcher("/pages/doctor/home.jsp").forward(request, response);
            } else {
                ControllerUtils.processNonexistentUserWithValidSessionParams(session, request, response);
            }
        } catch (SQLException e) {
            log.error("500: SQLException at doctor/home/HomePageServlet: " + e.getMessage());
            request.getRequestDispatcher("/pages/errors/500.html").forward(request, response);
        }
    }

}
