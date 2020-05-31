package com.Clinic_Accounting_System.servlets.registration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "RegSubmitNewAccountFormServlet", urlPatterns = "/registration/submitNewAccountApplicationForm")
public class SubmitNewAccountFormServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // creating new session to store params or error messages there
        HttpSession session = request.getSession(true);
        // scraping params from request
        String reg_username = request.getParameter("reg_username");
        String reg_password = request.getParameter("reg_password");
        // checking on param existence
        if(reg_username.length() >= 8 || reg_password.length() >= 8) {
            // checking if user with such username already exists in database
            if(!userService.existsByUsername(reg_username)){
                // moving username and pwd params from request scope to session scope
                session.setAttribute("reg_username", reg_username);
                session.setAttribute("reg_password", reg_password);
                response.sendRedirect("/registration/pers_info_application");
            } else {
                ControllerUtils.giveTicketToMyMessage(session, "Sorry, but user with such username already exists!");
                response.sendRedirect("/registration");
            }
        } else {
            ControllerUtils.giveTicketToMyMessage(session, "Username and password length should be no less then 8 characters!");
            response.sendRedirect("/registration");
        }
    }

}
