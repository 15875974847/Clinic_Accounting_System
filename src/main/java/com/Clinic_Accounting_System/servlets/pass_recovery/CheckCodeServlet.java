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
@WebServlet(name = "CheckCodeServlet", urlPatterns = "/pass_recovery/checkCode")
public class CheckCodeServlet extends HttpServlet {

    private final UserDAO userDAO = UserDAO.getInstance();
    private final CredUtils credUtils = CredUtils.getInstance();

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            // firstly, check if we have "forgot_page_user_id" attrib in session
            if(checkForgotPageUsernameAttribsInSession(session)){
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
                            response.sendRedirect("/sign_in");
                        } else {
                            ControllerUtils.giveTicketToMyMessage(session, "Password is less then 8 characters!");
                            response.sendRedirect("/pass_recovery/enter_email_code");
                        }
                    } else {
                        ControllerUtils.giveTicketToMyMessage(session, "Wrong verification code!");
                        response.sendRedirect("/pass_recovery/enter_email_code");
                    }
                } else {
                    ControllerUtils.giveTicketToMyMessage(session, "Sorry, but somebody already deleted your user account");
                    response.sendRedirect("/sign_in");
                }
            } else {
                response.sendRedirect("/pass_recovery");
            }
        } catch (SQLException e) {
            log.error("500: SQLException at /pass_recovery/CheckCodeServlet");
            request.getRequestDispatcher("errors/500.html").forward(request, response);
        }
    }

    private boolean checkForgotPageUsernameAttribsInSession(HttpSession session){
        if(session != null){
            Long id = (Long)session.getAttribute("pass_recovery_user_id");
            String username = (String)session.getAttribute("pass_recovery_username");
            return username != null && id != null;
        } else {
            return false;
        }
    }

}
