package com.Clinic_Accounting_System.servlets.pass_recovery;

import com.Clinic_Accounting_System.dao.UserDAO;
import com.Clinic_Accounting_System.entities.User;
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
@WebServlet(name = "Recovery_EnterCodeServlet", urlPatterns = "/pass_recovery/enter_code")
public class EnterCodeServlet extends HttpServlet {

    private final UserDAO userDAO = UserDAO.getInstance();
    private final CredUtils credUtils = CredUtils.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(checkForgotPageUsernameAttribInSession(session)){
            ControllerUtils.goThru_MessageByTicket_System(session);
            request.getRequestDispatcher("/pages/forgot/enter_email_code.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/pass_recovery");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            // firstly, check if we have "forgot_page_user_id" attrib in session
            if(checkForgotPageUsernameAttribInSession(session)){
                // take "email_code" from request
                String code = request.getParameter("email_code");
                User user = userDAO.getById((Long)session.getAttribute("pass_recovery_user_id"));
                if(code != null && user != null){
                    // if hashcode of password equals entered code
                    if(code.equals(credUtils.getHash(user.getPassword()))){
                        // take new password from request scope
                        String newPassword = request.getParameter("new_password");
                        // check if password is following requirements, you can add yours
                        if(credUtils.validate(newPassword)){
                            // update password in db
                            userDAO.updatePassword(user.getId(), newPassword);
                            // notify user that password successfully changed and go sign in
                            ControllerUtils.giveTicketToMyMessage(session, "Password successfully changed!");
                            removeForgotPageAttributeFromSession(session);
                            response.sendRedirect(request.getContextPath() + "/sign_in");
                        } else {
                            ControllerUtils.giveTicketToMyMessage(session, "Password is less then 8 characters!");
                            response.sendRedirect(request.getContextPath() + "/pass_recovery/enter_code");
                        }
                    } else {
                        ControllerUtils.giveTicketToMyMessage(session, "Wrong verification code!");
                        response.sendRedirect(request.getContextPath() + "/pass_recovery/enter_code");
                    }
                } else {
                    ControllerUtils.giveTicketToMyMessage(session, "Sorry, we can't restore your password.");
                    removeForgotPageAttributeFromSession(session);
                    response.sendRedirect(request.getContextPath() + "/sign_in");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/pass_recovery");
            }
        } catch (SQLException e) {
            log.error("500: SQLException at /pass_recovery/CheckCodeServlet: " + e.getMessage());
            request.getRequestDispatcher("/pages/errors/500.jsp").forward(request, response);
        }
    }

    private boolean checkForgotPageUsernameAttribInSession(HttpSession session){
        if(session != null){
            Long id = (Long)session.getAttribute("pass_recovery_user_id");
            return id != null;
        } else {
            return false;
        }
    }

    private void removeForgotPageAttributeFromSession(HttpSession session){
        session.removeAttribute("pass_recovery_user_id");
    }

}
