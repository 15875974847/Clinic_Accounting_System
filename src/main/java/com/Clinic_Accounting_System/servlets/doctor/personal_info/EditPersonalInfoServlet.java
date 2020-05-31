package com.Clinic_Accounting_System.servlets.doctor.personal_info;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "DoctorsEditPersonalInfoServlet", urlPatterns = "/doctor/editPersonalInfo")
public class EditPersonalInfoServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        // making call to db to get reference to doctor info object
        Doctors doctorInfo = doctorsService.getOne((Long)session.getAttribute("user_id"));
        if(doctorInfo != null){
            // retrieving parameters from request
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
            // setting updated doctor's info params
            doctorInfo.getStaffEntity().getUserInfo().setFirstName(firstname);
            doctorInfo.getStaffEntity().getUserInfo().setMiddleName(midname);
            doctorInfo.getStaffEntity().getUserInfo().setLastName(lastname);
            doctorInfo.getStaffEntity().getUserInfo().setEmail(email);
            doctorInfo.getStaffEntity().getUserInfo().setPhone(phone);
            doctorInfo.getStaffEntity().getUserInfo().setDateOfBirth(dob);
            doctorInfo.getStaffEntity().getUserInfo().setAddress(address);
            doctorInfo.setSpecialization(specialization);
            doctorInfo.setDegree(degree);
            // and flush changes to db
            doctorsService.saveAndFlush(doctorInfo);
            // give ticket to message about successful update
            ControllerUtils.giveTicketToMyMessage(session, "Personal information successfully updated!");
            response.sendRedirect("/doctor/pers_info");
    }

}
