package com.Clinic_Accounting_System.servlets.admin.patients;

import com.Clinic_Accounting_System.dao.PatientDAO;
import com.Clinic_Accounting_System.dao.UserDAO;
import com.Clinic_Accounting_System.entities.Patient;
import com.Clinic_Accounting_System.entities.User;
import com.Clinic_Accounting_System.utils.ControllerUtils;
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
@WebServlet(name = "AdminsEditPatientServlet", urlPatterns = "/admin/editPatient")
public class EditPatientServlet extends HttpServlet {

    private final UserDAO userDAO = UserDAO.getInstance();
    private final PatientDAO patientDAO = PatientDAO.getInstance();

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            // fetch params from request
            Long patientID = Long.parseLong(request.getParameter("patientID"));
            // check if such patient exists
            User user = userDAO.getById(patientID);
            if(user != null){
                // continue fetching
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String firstname = request.getParameter("firstname");
                String midname = request.getParameter("midname");
                String lastname = request.getParameter("lastname");
                Date dob = java.sql.Date.valueOf(request.getParameter("dob"));
                ControllerUtils.makeCorrectionForTimeZone(dob);
                String phone = request.getParameter("phone");
                String email = request.getParameter("email");
                String address = request.getParameter("address");

                // update data in Users
                user.setUsername(username);
                user.setPassword(password);
                userDAO.updateUsernameAndPassword(user.getId(), username, password);

                // update data in UserInfo
                patientDAO.updateAllPatientInfoExceptMedHistory(patientID, firstname, lastname, midname,
                        dob, email, phone, address);

                // give admin notification about successful update operation
                ControllerUtils.giveTicketToMyMessage(session, "Patient's info successfully updated!");
            } else {
                ControllerUtils.giveTicketToMyMessage(session, "User with such id don't exist anymore");
            }
            response.sendRedirect(request.getContextPath() + "/admin/patients");
        } catch(SQLException e){
            log.error("500: SQLException at admin/patients/DeletePatientServlet: " + e.getMessage());
            request.getRequestDispatcher("/pages/errors/500.html").forward(request, response);
        }
    }

}
