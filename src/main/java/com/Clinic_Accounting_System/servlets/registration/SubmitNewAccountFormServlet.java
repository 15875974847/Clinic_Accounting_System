package com.Clinic_Accounting_System.servlets.registration;

import com.Clinic_Accounting_System.dao.UserDAO;
import com.Clinic_Accounting_System.utils.ControllerUtils;
import com.Clinic_Accounting_System.utils.CredUtils;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@Log4j2
@WebServlet(name = "RegSubmitNewAccountFormServlet", urlPatterns = "/registration/submitNewAccountApplicationForm")
public class SubmitNewAccountFormServlet extends HttpServlet {

    private final UserDAO userDAO = UserDAO.getInstance();
    private final CredUtils credUtils = CredUtils.getInstance();

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            // create new session to store params or error messages there
            HttpSession session = request.getSession(true);
            // scrap params from request
            String reg_username = request.getParameter("reg_username");
            String reg_password = request.getParameter("reg_password");
            // validate params
            if(credUtils.validate(reg_username) && credUtils.validate(reg_password)) {
                // check if user with such username already exists in database
                if(!userDAO.existsByUsername(reg_username)) {
                    // set username and pwd params at session scope
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
        } catch (SQLException e) {
            log.error("500: SQLException at registration/SubmitNEwAccountInfoServlet");
            response.sendRedirect("/errors/500.html");
        }
    }

}
