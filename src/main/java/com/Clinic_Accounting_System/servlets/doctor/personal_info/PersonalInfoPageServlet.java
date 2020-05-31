package com.Clinic_Accounting_System.servlets.doctor.personal_info;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "DoctorsPersonalInfoPageServlet", urlPatterns = "/doctor/pers_info")
public class PersonalInfoPageServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // making call to patient service repo to retrieve doctor info from database
        HttpSession session = request.getSession();
        Optional<Doctors> doctorInfo = doctorsService.findById((Long)session.getAttribute("user_id"));
        if(doctorInfo.isPresent()){
            // setting doctor obj as request parameter
            request.setAttribute("doctor", doctorInfo.get());
            // go thought message-by-ticket system
            ControllerUtils.goThru_MessageByTicket_System(session);
            request.getRequestDispatcher("doctor/pers_info.jsp").forward(request, response);
        } else {
            return ControllerUtils.processNonexistentUserWithValidSessionParams(session, request);
        }
    }

}