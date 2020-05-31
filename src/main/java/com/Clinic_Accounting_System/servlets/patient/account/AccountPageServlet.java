package com.Clinic_Accounting_System.servlets.patient.account;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;


@WebServlet(name = "PatientsAccountPageServlet", urlPatterns = "/patient/account")
public class AccountPageServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // making some calls to retrieve account info from database
        HttpSession session = request.getSession();
        Optional<Users> accountInfo = accountInfoService.findById((Long)session.getAttribute("user_id"));
        if(accountInfo.isPresent()){
            // setting request params, so jsp can render the page
            request.setAttribute("username", accountInfo.get().getUsername());
            request.setAttribute("password", accountInfo.get().getPassword());
            // go thought message-by-ticket system
            ControllerUtils.goThru_MessageByTicket_System(session);
            request.getRequestDispatcher("patient/account.jsp").forward(request, response);
        } else {
            return ControllerUtils.processNonexistentUserWithValidSessionParams(session, request);
        }
    }

}
