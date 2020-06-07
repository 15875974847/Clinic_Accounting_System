package com.Clinic_Accounting_System.servlets.patient.account;

import com.Clinic_Accounting_System.dao.UserDAO;
import com.Clinic_Accounting_System.entities.User;
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
@WebServlet(name = "PatientsAccountPageServlet", urlPatterns = "/patient/account")
public class AccountPageServlet extends HttpServlet {

    private final UserDAO userDAO = UserDAO.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            // fetch user from db
            HttpSession session = request.getSession();
            User user = userDAO.getById((Long)session.getAttribute("user_id"));
            if(user != null){
                // set request attribs, so jsp can render the page
                request.setAttribute("username", user.getUsername());
                request.setAttribute("password", user.getPassword());
                // go thought message-by-ticket system
                ControllerUtils.goThru_MessageByTicket_System(session);
                request.getRequestDispatcher("/pages/patient/account.jsp").forward(request, response);
            } else {
                ControllerUtils.processNonexistentUserWithValidSessionParams(session, request, response);
            }
        } catch (SQLException e) {
            log.error("500: SQLException at patient/account/AccountPageServlet: " + e.getMessage());
            request.getRequestDispatcher("/pages/errors/500.jsp").forward(request, response);
        }
    }

}
