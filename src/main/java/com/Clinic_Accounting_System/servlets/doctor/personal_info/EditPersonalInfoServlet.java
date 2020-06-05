package com.Clinic_Accounting_System.servlets.doctor.personal_info;

import com.Clinic_Accounting_System.dao.DoctorDAO;
import com.Clinic_Accounting_System.dao.PatientDAO;
import com.Clinic_Accounting_System.dao.UserDAO;
import com.Clinic_Accounting_System.entities.Doctor;
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
@WebServlet(name = "DoctorsEditPersonalInfoServlet", urlPatterns = "/doctor/editPersonalInfo")
public class EditPersonalInfoServlet extends HttpServlet {

    private final DoctorDAO doctorDAO = DoctorDAO.getInstance();
    private final PatientDAO userInfoDAO = PatientDAO.getInstance();

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            final Long userID = (Long)session.getAttribute("user_id");
            // fetch doctor's info from db
            final Patient userInfo = userInfoDAO.getPatientById(userID);
            final Doctor doctor = doctorDAO.getDoctorById(userID);
            if(userInfo != null && doctor != null) {
                // retrieve parameters from request
                String firstname = request.getParameter("newFirstname");
                String midname = request.getParameter("newMidname");
                String lastname = request.getParameter("newLastname");
                String email = request.getParameter("newEmail");
                String phone = request.getParameter("newPhone");
                Date dob = java.sql.Date.valueOf(request.getParameter("newDOB"));
                ControllerUtils.makeCorrectionForTimeZone(dob);
                String address = request.getParameter("newAddress");
                String specialization = request.getParameter("newSpecialization");
                String degree = request.getParameter("newDegree");

                // update doctor's info in db
                userInfoDAO.updateAllPatientInfoExceptMedHistory(userID, firstname, lastname,
                                                        midname, dob, email, phone, address);
                doctorDAO.updateDoctor(userID, degree, specialization);

                // give ticket to message about successful update
                ControllerUtils.giveTicketToMyMessage(session, "Personal information successfully updated!");
                response.sendRedirect(request.getContextPath() + "/doctor/pers_info");
            }
        } catch(SQLException e) {
            log.error("500: SQLException at doctor/pers_info/EditPersonalInfoServlet: " + e.getMessage());
            request.getRequestDispatcher("/pages/errors/500.html").forward(request, response);
        }
    }

}
