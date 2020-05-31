package com.Clinic_Accounting_System.servlets.pass_recovery;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "PassRecoveryPageServlet", urlPatterns = "/pass_recovery")
public class PassRecoveryPageServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        ControllerUtils.goThru_MessageByTicket_System(session);
        request.getRequestDispatcher("pass_recovery/get_username.jsp").forward(request, response);
    }

}
