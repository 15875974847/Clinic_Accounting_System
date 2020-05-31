package com.Clinic_Accounting_System.servlets.doctor.home;

import com.Clinic_Accounting_System.utils.ControllerUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "DoctorsHomePageServlet", urlPatterns = "/doctor/home")
public class HomePageServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // making some calls to db to fetch necessary info
        HttpSession session = request.getSession();
        // look for name to greet user
        Optional<UserInfo> patientInfo = patientInfoService.findById((Long)session.getAttribute("user_id"));
        if(patientInfo.isPresent()){
            // setting firstname to greet user
            request.setAttribute("user", patientInfo.get().getFirstName());
            // search events for doctor
            List<Events> events = eventsService.findAll();
            request.setAttribute("events", events);
            // process thru message-by-ticket system
            ControllerUtils.goThru_MessageByTicket_System(session);
            request.getRequestDispatcher("doctor/home.jsp").forward(request, response);
        } else {
            return ControllerUtils.processNonexistentUserWithValidSessionParams(session, request);
        }
    }

}
