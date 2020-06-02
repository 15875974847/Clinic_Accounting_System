package com.Clinic_Accounting_System.servlets.registration;

import com.Clinic_Accounting_System.utils.ControllerUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "RegAccountInfoFormServlet", urlPatterns = "/registration")
public class AccountInfoFormServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        // going thru message by ticket system
        ControllerUtils.goThru_MessageByTicket_System(session);
        request.getRequestDispatcher("registration/new_account_application.jsp").forward(request, response);
    }

}
