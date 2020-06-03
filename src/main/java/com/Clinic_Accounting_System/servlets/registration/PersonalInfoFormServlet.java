package com.Clinic_Accounting_System.servlets.registration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "RegPersonalInfoFormServlet", urlPatterns = "/registration/pers_info_application")
public class PersonalInfoFormServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(checkSessionAttribsForRegOnExistence(request)){
            request.getRequestDispatcher("registration/pers_info_application.jsp").forward(request, response);
        } else {
            response.sendRedirect("/registration");
        }
    }

    private boolean checkSessionAttribsForRegOnExistence(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return session.getAttribute("reg_username") != null && session.getAttribute("reg_password") != null;
        }
        return false;
    }

}
