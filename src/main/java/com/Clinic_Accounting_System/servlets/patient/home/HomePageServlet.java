package com.Clinic_Accounting_System.servlets.patient.home;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;


@WebServlet(name = "PatientsHomePageServlet", urlPatterns = "/patient/home")
public class HomePageServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // making some calls to db to fetch necessary info
        HttpSession session = request.getSession();
        // look for name to greet user
        Optional<UserInfo> patientInfo = patientInfoService.findById((Long)session.getAttribute("user_id"));
        if(patientInfo.isPresent()){
            // setting firstname as request attribute to loose it after redirection
            request.setAttribute("user", patientInfo.get().getFirstName());
            // search events for patient
            ArrayList<Events> events = eventsService.findAllByOnlyForPersonal(false);
            request.setAttribute("events", events);
            request.getRequestDispatcher("patient/home.jsp").forward(request, response);
        } else {
            return ControllerUtils.processNonexistentUserWithValidSessionParams(session, request);
        }
    }

}