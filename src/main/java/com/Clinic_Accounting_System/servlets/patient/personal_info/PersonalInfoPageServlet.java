package com.Clinic_Accounting_System.servlets.patient.personal_info;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "PatientsPersonalInfoPageServlet", urlPatterns = "/patient/pers_info")
public class PersonalInfoPageServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // making call to patient service repo to retrieve user info from database
        HttpSession session = request.getSession();
        Optional<UserInfo> patientInfo = patientInfoService.findById((Long)session.getAttribute("user_id"));
        if(patientInfo.isPresent()){
            // setting request params, so jsp can render the page
            request.setAttribute("firstname", patientInfo.get().getFirstName());
            request.setAttribute("midname", patientInfo.get().getMiddleName());
            request.setAttribute("lastname", patientInfo.get().getLastName());
            request.setAttribute("email", patientInfo.get().getEmail());
            request.setAttribute("phone", patientInfo.get().getPhone());
            request.setAttribute("dob", patientInfo.get().getDateOfBirth().toString());
            request.setAttribute("address", patientInfo.get().getAddress());
            request.setAttribute("medHistory", patientInfo.get().getMedicalHistory());
            request.getRequestDispatcher("patient/pers_info.jsp").forward(request, response);
        } else {
            return ControllerUtils.processNonexistentUserWithValidSessionParams(session, request);
        }
    }

}
