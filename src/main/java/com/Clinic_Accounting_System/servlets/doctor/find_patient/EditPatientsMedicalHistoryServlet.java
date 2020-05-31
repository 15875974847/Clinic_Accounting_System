package com.Clinic_Accounting_System.servlets.doctor.find_patient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(name = "DoctorsEditPatientsMedicalHistoryServlet", urlPatterns = "/doctor/changePatientsMedicalHistory")
public class EditPatientsMedicalHistoryServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        // retrieving params from request
        Long patientID = Long.parseLong(request.getParameter("patientID"));
        String newMedicalHistory = request.getParameter("newMedicalHistory");
        // making call to database to make LAZY fetch of patient info
        UserInfo patient = patientInfoService.getOne(patientID);
        if(patient != null){
            // update it's medical history
            patient.setMedicalHistory(newMedicalHistory);
            // and flush changes
            patientInfoService.saveAndFlush(patient);
            // give message about successful update
            ControllerUtils.giveTicketToMyMessage(session, "Medical history successfully updated!");
            response.sendRedirect("/doctor/find_patient");
        } else {
            ControllerUtils.giveTicketToMyMessage(session, "Such patient does not exist anymore!");
            response.sendRedirect("/doctor/find_patient");
        }
    }

}
