package com.Clinic_Accounting_System.servlets.patient.personal_info;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "PatientsEditPersonalInfoServlet", urlPatterns = "/patient/editPersonalInfo")
public class EditPersonalInfoServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        // making call to db to get reference to patient info object
        UserInfo patientInfo = patientInfoService.getOne((Long)session.getAttribute("user_id"));
        if(patientInfo != null){
            // gettin' params from request
            String firstname = request.getParameter("firstname");
            String midname = request.getParameter("midname");
            String lastname = request.getParameter("lastname");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            Date dob = java.sql.Date.valueOf(request.getParameter("dob"));
            ControllerUtils.makeCorrectionForTimeZone(dob);
            String address = request.getParameter("address");
            // setting updated patient info params
            patientInfo.setFirstName(firstname);
            patientInfo.setMiddleName(midname);
            patientInfo.setLastName(lastname);
            patientInfo.setEmail(email);
            patientInfo.setPhone(phone);
            patientInfo.setDateOfBirth(dob);
            patientInfo.setAddress(address);
            // and apply changes to db
            patientInfoService.saveAndFlush(patientInfo);
            response.sendRedirect("/patient/pers_info");
        } else {
            return ControllerUtils.processNonexistentUserWithValidSessionParams(session, request);
        }
    }

}
