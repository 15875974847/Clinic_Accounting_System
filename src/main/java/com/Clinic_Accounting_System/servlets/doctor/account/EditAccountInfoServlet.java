package com.Clinic_Accounting_System.servlets.doctor.account;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "DoctorsEditAccountInfoServlet", urlPatterns = "/doctor/editAccountInfo")
public class EditAccountInfoServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String oldUsername = request.getParameter("oldUsername");
        String oldPassword = request.getParameter("oldPassword");
        String newUsername = request.getParameter("newUsername");
        String newPassword = request.getParameter("newPassword");

        HttpSession session = request.getSession();
        if(!oldUsername.equals(newUsername) || !oldPassword.equals(newPassword)){
            // then making some calls to retrieve user account info from database
            // this method returns a reference to the entity with the given identifier, ie we JPA will execute SQL update statement on save method
            Users accountInfo = accountInfoService.getOne((Long)session.getAttribute("user_id"));
            if(accountInfo != null){
                // resetting username and password parameters
                accountInfo.setUsername(newUsername);
                accountInfo.setPassword(newPassword);
                accountInfoService.saveAndFlush(accountInfo);
                // give ticket to successful username and password update message
                ControllerUtils.giveTicketToMyMessage(session, "Username and password updated!");
                response.sendRedirect("/doctor/account");
            } else {
                return ControllerUtils.processNonexistentUserWithValidSessionParams(session, request);
            }
        } else {
            // if user entered same params redirect at the same page and display an error message
            ControllerUtils.giveTicketToMyMessage(session, "U entered same params!");
            response.sendRedirect("/doctor/account");
        }
    }

}
