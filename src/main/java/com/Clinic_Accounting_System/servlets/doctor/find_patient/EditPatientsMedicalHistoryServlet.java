package com.Clinic_Accounting_System.servlets.doctor.find_patient;

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
import java.sql.SQLException;

@Log4j2
@WebServlet(name = "DoctorsEditPatientsMedicalHistoryServlet", urlPatterns = "/doctor/changePatientsMedicalHistory")
public class EditPatientsMedicalHistoryServlet extends HttpServlet {

    private final PatientDAO patientDAO = PatientDAO.getInstance();

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            // retrieve params from request
            Long patientID = Long.parseLong(request.getParameter("patientID"));
            String newMedicalHistory = request.getParameter("newMedicalHistory");
            // fetch patient from db
            Patient patient = patientDAO.getPatientById(patientID);
            if(patient != null){
                // update patient's medical history
                patientDAO.updatePatientMedicalHistoryById(patient.getId(), newMedicalHistory);
                ControllerUtils.giveTicketToMyMessage(session, "Patient's medical history successfully updated!");
            } else {
                ControllerUtils.giveTicketToMyMessage(session, "Patient account does not exist anymore!");
            }
            response.sendRedirect(request.getContextPath() + "/doctor/find_patient");
        } catch (SQLException e) {
            log.error("500: SQLException at doctor/find_patient/EditPatientsMedHistoryServlet: " + e.getMessage());
            request.getRequestDispatcher("/pages/errors/500.jsp").forward(request, response);
        }
    }

}
