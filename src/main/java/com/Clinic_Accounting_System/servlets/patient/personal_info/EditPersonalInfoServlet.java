package com.Clinic_Accounting_System.servlets.patient.personal_info;

import com.Clinic_Accounting_System.dao.PatientDAO;
import com.Clinic_Accounting_System.entities.Patient;
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
@WebServlet(name = "PatientsEditPersonalInfoServlet", urlPatterns = "/patient/editPersonalInfo")
public class EditPersonalInfoServlet extends HttpServlet {

    private final PatientDAO patientDAO = PatientDAO.getInstance();

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            // fetch patient from db
            Patient patient = patientDAO.getPatientById((Long)session.getAttribute("user_id"));
            if(patient != null) {
                // get params from request
                String firstname = request.getParameter("firstname");
                String midname = request.getParameter("midname");
                String lastname = request.getParameter("lastname");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                Date dob = java.sql.Date.valueOf(request.getParameter("dob"));
                ControllerUtils.makeCorrectionForTimeZone(dob);
                String address = request.getParameter("address");
                // update patient's info in db
                patientDAO.updateAllPatientInfoExceptMedHistory(patient.getId(), firstname, lastname, midname,
                                                                    dob, email, phone, address);
                // go thru message-by-ticket system
                ControllerUtils.giveTicketToMyMessage(session, "Information successfully updated!");
                response.sendRedirect(request.getContextPath() + "/patient/pers_info");
            } else {
                ControllerUtils.processNonexistentUserWithValidSessionParams(session, request, response);
            }
        } catch (SQLException e) {
            log.error("500: SQLException at patient/personal_info/EditPersonalInfoServlet: " + e.getMessage());
            request.getRequestDispatcher("/pages/errors/500.html").forward(request, response);
        }
    }

}
