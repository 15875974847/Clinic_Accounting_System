package com.Clinic_Accounting_System.servlets.pass_recovery;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "CheckCodeServlet", urlPatterns = "/pass_recovery/checkCode")
public class CheckCodeServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        // firstly, checking if we have "forgot_page_user_id" attrib
        if(checkForgotPageUsernameAttribInSession(session)){
            // taking "email_code" from request
            String code = request.getParameter("email_code");
            Users user = userService.getOne((Long)session.getAttribute("pass_recovery_user_id"));
            if(code != null && user != null){
                // if hashcode of password equals entered code
                if(code.equals(Integer.toString(user.getPassword().hashCode()))){
                    // taking new password from request scope
                    String newPassword = request.getParameter("new_password");
                    // check if password is following requirements, you can add yours
                    if(newPassword.length() >= 8){
                        // and update it in database
                        user.setPassword(newPassword);
                        userService.saveAndFlush(user);
                        // then notify user that password successfully changed and go sign in
                        ControllerUtils.giveTicketToMyMessage(session, "Password successfully changed!");
                        response.sendRedirect("/sign_in");
                    } else{
                        ControllerUtils.giveTicketToMyMessage(session, "Password is less then 8 characters!");
                        response.sendRedirect("/pass_recovery/enter_email_code");
                    }
                } else {
                    ControllerUtils.giveTicketToMyMessage(session, "Sorry, but u entered wrong code! Try once again!");
                    response.sendRedirect("/pass_recovery/enter_email_code");
                }
            } else {
                ControllerUtils.giveTicketToMyMessage(session, "Sorry, but somebody already deleted your user account");
                response.sendRedirect("/sign_in");
            }
        } else {
            response.sendRedirect("/pass_recovery");
        }
    }

}
