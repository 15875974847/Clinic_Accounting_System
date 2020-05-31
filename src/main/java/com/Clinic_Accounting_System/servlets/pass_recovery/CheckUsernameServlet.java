package com.Clinic_Accounting_System.servlets.pass_recovery;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "CheckUsernameServlet", urlPatterns = "/pass_recovery/checkUsername")
public class CheckUsernameServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        // scrapping username from request
        String username = request.getParameter("username");
        // checking in database if user with such username exists
        Users user = userService.getByUsername(username);
        if(user != null){
            // checking if user info profile with such use_id exists
            UserInfo userInfo = userInfoService.getOne(user.getId());
            if(userInfo != null){
                try {
                    // moving "forgot_page_user_id" and "forgot_page_username" tokens to have access to next pages
                    session.setAttribute("pass_recovery_user_id", user.getId());
                    session.setAttribute("pass_recovery_username", username);
                    // then try to send code to email from profile
                    emailService.sendCodeToRestorePassword(userInfo.getEmail(), "Hey buddy, it's Arti's CAS password recovery system", "Your code: " + user.getPassword().hashCode());
                    response.sendRedirect("/pass_recovery/enter_email_code");
                } catch (MailException e){
                    ControllerUtils.giveTicketToMyMessage(session, "Sorry, but we cannot send your code to restore password on provided email!");
                    response.sendRedirect("/sign_in");
                }
            } else{
                ControllerUtils.giveTicketToMyMessage(session, "Sorry, but your profile info does not exist!");
                response.sendRedirect("/sign_in");
            }
        } else {
            ControllerUtils.giveTicketToMyMessage(session, "User with such username does not exist!");
            response.sendRedirect("/pass_recovery");
        }
    }

}
