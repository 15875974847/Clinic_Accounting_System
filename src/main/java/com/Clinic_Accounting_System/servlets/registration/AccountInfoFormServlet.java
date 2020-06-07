package com.Clinic_Accounting_System.servlets.registration;

import com.Clinic_Accounting_System.dao.PatientDAO;
import com.Clinic_Accounting_System.dao.UserDAO;
import com.Clinic_Accounting_System.entities.User;
import com.Clinic_Accounting_System.utils.ControllerUtils;
import com.Clinic_Accounting_System.utils.CredUtils;
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
@WebServlet(name = "Registration_AccountInfoFormServlet", urlPatterns = "/registration")
public class AccountInfoFormServlet extends HttpServlet {

    private final UserDAO userDAO = UserDAO.getInstance();
    private final PatientDAO patientDAO = PatientDAO.getInstance();
    private final CredUtils credUtils = CredUtils.getInstance();


    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        // go thru message by ticket system
        ControllerUtils.goThru_MessageByTicket_System(session);
        request.getRequestDispatcher("/pages/registration/registration_form.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            // create new session to store error messages there
            HttpSession session = request.getSession(true);
            // scrap creds from request
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            // scrap userInfo from request scope
            String firstname = request.getParameter("firstname");
            String midname = request.getParameter("midname");
            String lastname = request.getParameter("lastname");
            Date dob = java.sql.Date.valueOf(request.getParameter("dob"));
            ControllerUtils.makeCorrectionForTimeZone(dob);
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String homeAddress = request.getParameter("homeAddress");

            // validate params
            if(credUtils.validate(username) && credUtils.validate(password)) {
                // check if user with such username already exists in database
                if(!userDAO.existsByUsername(username)) {
                    // persist new user in database
                    userDAO.createUser(username, password, Roles.patient.name());
                    // persist user info in database
                    User user = userDAO.getByUsername(username);
                    patientDAO.createPatient(user.getId(), firstname, lastname, midname, dob,
                                                email, phone, homeAddress, "");
                    // notify user about successful sign up
                    ControllerUtils.giveTicketToMyMessage(session, "Successful sign up! Please, log in.");
                    response.sendRedirect(request.getContextPath() + "/sign_in");
                } else {
                    ControllerUtils.giveTicketToMyMessage(session, "Sorry, but user with such username already exists!");
                    response.sendRedirect(request.getContextPath() + "/registration");
                }
            } else {
                ControllerUtils.giveTicketToMyMessage(session, "Username and password length should be no less then 8 characters!");
                response.sendRedirect(request.getContextPath() + "/registration");
            }
        } catch (SQLException e) {
            log.error("500: SQLException at registration/SubmitNewAccountInfoServlet: " + e.getMessage());
            request.getRequestDispatcher("/pages/errors/500.jsp").forward(request, response);
        }
    }

}
