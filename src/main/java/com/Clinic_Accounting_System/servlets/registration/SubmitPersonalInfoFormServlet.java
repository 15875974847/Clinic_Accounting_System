package com.Clinic_Accounting_System.servlets.registration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "RegSubmitPersonalInfoFormServlet", urlPatterns = "/registration/submitPersonalInformationForm")
public class SubmitPersonalInfoFormServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        // here could be server's parameters validation, but let's omit them for brevity
        // to be sure checking once again if nobody took our username
        if(!userService.existsByUsername((String)session.getAttribute("reg_username"))){
            // persisting new user in database
            com.Clinic_Accounting_System.Clinic_Accounting_System.models.Users user = new com.Clinic_Accounting_System.Clinic_Accounting_System.models.Users((String)session.getAttribute("reg_username"), (String)session.getAttribute("reg_password"), com.Clinic_Accounting_System.Clinic_Accounting_System.models.Roles.user);
            userService.saveAndFlush(user);
            // scrapping user info from form params
            String firstname = request.getParameter("firstname");
            String midname = request.getParameter("midname");
            String lastname = request.getParameter("lastname");
            Date dob = java.sql.Date.valueOf(request.getParameter("dob"));
            ControllerUtils.makeCorrectionForTimeZone(dob);
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String homeAddress = request.getParameter("homeAddress");
            // persist user info in database
            com.Clinic_Accounting_System.Clinic_Accounting_System.models.UserInfo userInfo = new com.Clinic_Accounting_System.Clinic_Accounting_System.models.UserInfo(firstname, midname, lastname, dob, phone, email, homeAddress, user);
            userInfoService.saveAndFlush(userInfo);
            // take registration username and password
            session.removeAttribute("reg_username");
            session.removeAttribute("reg_password");
            // and redirect new user to sign in page
            response.sendRedirect("/sign_in");
        } else {
            ControllerUtils.giveTicketToMyMessage(session, "Sorry, but your username somebody already took!");
            response.sendRedirect("/registration");
        }
    }

}
