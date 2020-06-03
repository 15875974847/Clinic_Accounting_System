package com.Clinic_Accounting_System.servlets.registration;

import com.Clinic_Accounting_System.dao.PatientDAO;
import com.Clinic_Accounting_System.dao.UserDAO;
import com.Clinic_Accounting_System.entities.User;
import com.Clinic_Accounting_System.utils.ControllerUtils;
import com.Clinic_Accounting_System.utils.Roles;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

@Log4j2
@WebServlet(name = "RegSubmitPersonalInfoFormServlet", urlPatterns = "/registration/submitPersonalInformationForm")
public class SubmitPersonalInfoFormServlet extends HttpServlet {

    private final UserDAO userDAO = UserDAO.getInstance();
    private final PatientDAO patientDAO = PatientDAO.getInstance();

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            HttpSession session = request.getSession();
            final String username = (String)session.getAttribute("reg_username");
            final String password = (String)session.getAttribute("reg_password");
            // check once again to be sure nobody took our username
            if(!userDAO.existsByUsername(username)){
                // persist new user in database
                userDAO.createUser(username, password, Roles.user.name());
                // scrap user info from request scope
                String firstname = request.getParameter("firstname");
                String midname = request.getParameter("midname");
                String lastname = request.getParameter("lastname");
                Date dob = java.sql.Date.valueOf(request.getParameter("dob"));
                ControllerUtils.makeCorrectionForTimeZone(dob);
                String phone = request.getParameter("phone");
                String email = request.getParameter("email");
                String homeAddress = request.getParameter("homeAddress");
                // persist user info in database
                User user = userDAO.getByUsername(username);
                patientDAO.createPatient(user.getId(), firstname, lastname, midname, dob,
                                            email, phone, homeAddress, "");
                // take registration attribs from session
                session.removeAttribute("reg_username");
                session.removeAttribute("reg_password");
                // redirect new user to sign in page
                response.sendRedirect("/sign_in");
            } else {
                ControllerUtils.giveTicketToMyMessage(session, "Sorry, but your username somebody already took!");
                response.sendRedirect("/registration");
            }
        } catch(SQLException e) {
            log.error("500: SQLException at registration/SubmitPersonalInfoFormServlet");
            response.sendRedirect("/errors/500.html");
        }
    }

}
