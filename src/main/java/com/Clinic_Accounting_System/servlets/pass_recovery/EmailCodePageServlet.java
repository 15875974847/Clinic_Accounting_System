package com.Clinic_Accounting_System.servlets.pass_recovery;

import com.Clinic_Accounting_System.utils.ControllerUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "EmailCodePageServlet", urlPatterns = "/pass_recovery/enter_email_code")
public class EmailCodePageServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(checkForgotPageUsernameAttribsInSession(session)){
            ControllerUtils.goThru_MessageByTicket_System(session);
            request.getRequestDispatcher("forgot/enter_email_code.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/pass_recovery").forward(request, response);
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
