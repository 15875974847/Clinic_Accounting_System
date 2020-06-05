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

@Log4j2
@WebServlet(name = "PatientsEditAccountInfoServlet", urlPatterns = "/patient/editAccountInfo")
public class EditAccountInfoServlet extends HttpServlet {

    private final UserDAO userDAO = UserDAO.getInstance();

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            String oldUsername = request.getParameter("oldUsername");
            String oldPassword = request.getParameter("oldPassword");
            String newUsername = request.getParameter("newUsername");
            String newPassword = request.getParameter("newPassword");

            HttpSession session = request.getSession();
            if(!oldUsername.equals(newUsername) || !oldPassword.equals(newPassword)){
                // fetch user from db
                User user = userDAO.getById((Long)session.getAttribute("user_id"));
                if(user != null){
                    // update username and password parameters
                    userDAO.updateUsernameAndPassword(user.getId(), newUsername, newPassword);
                    // give ticket to successful username and password update message
                    ControllerUtils.giveTicketToMyMessage(session, "Username and password updated!");
                    response.sendRedirect(request.getContextPath() + "/patient/account");
                } else {
                    ControllerUtils.processNonexistentUserWithValidSessionParams(session, request, response);
                }
            } else {
                // user entered same credentials
                ControllerUtils.giveTicketToMyMessage(session, "You entered same credentials!");
                response.sendRedirect(request.getContextPath() + "/patient/account");
            }
        } catch (SQLException e) {
            log.error("500: SQLException at patient/account/EditAccountInfoServlet: " + e.getMessage());
            request.getRequestDispatcher("/pages/errors/500.html").forward(request, response);
        }
    }

}
