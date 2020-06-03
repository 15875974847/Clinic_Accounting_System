package com.Clinic_Accounting_System.servlets.pass_recovery;

import com.Clinic_Accounting_System.dao.PatientDAO;
import com.Clinic_Accounting_System.dao.UserDAO;
import com.Clinic_Accounting_System.entities.Patient;
import com.Clinic_Accounting_System.entities.User;
import com.Clinic_Accounting_System.utils.ControllerUtils;
import com.Clinic_Accounting_System.utils.MailBot;
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
@WebServlet(name = "CheckUsernameServlet", urlPatterns = "/pass_recovery/checkUsername")
public class CheckUsernameServlet extends HttpServlet {

    private final UserDAO userDAO = UserDAO.getInstance();
    private final PatientDAO patientDAO = PatientDAO.getInstance();
    private final MailBot emailService = MailBot.getInstance();
    private final CredUtils credUtils = CredUtils.getInstance();

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            // scrap username from request scope
            String username = request.getParameter("username");
            // check in database if user with such username exists
            User user = userDAO.getByUsername(username);
            Patient patient = patientDAO.getPatientById(user.getId());
            if(user != null){
                // moving "forgot_page_user_id" and "forgot_page_username" tokens to have access to next pages
                session.setAttribute("pass_recovery_user_id", user.getId());
                session.setAttribute("pass_recovery_username", username);
                // then try to send code to email from profile
                if(emailService.sendMessage(patient.getEmail(),
                        "Arti's CAS password recovery system",
                        "Hey buddy, here is your code: " + credUtils.getHash(user.getPassword()) + ". Don't tell anybody!")){
                    response.sendRedirect("/pass_recovery/enter_email_code");
                } else {
                    // if send failed
                    ControllerUtils.giveTicketToMyMessage(session, "Sorry, but we cannot send your code to restore password on provided email!");
                    response.sendRedirect("/sign_in");
                }
            } else {
                ControllerUtils.giveTicketToMyMessage(session, "User with such username does not exist!");
                response.sendRedirect("/pass_recovery");
            }
        } catch (SQLException e) {
            log.error("500: SQLException at pass_recovery/CheckUsernameServlet");
            response.sendRedirect("/errors/500.html");
        }
    }

}
