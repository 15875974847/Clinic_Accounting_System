package com.Clinic_Accounting_System.servlets.admin.patients;

import com.Clinic_Accounting_System.utils.ControllerUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "AdminsEditPatientServlet", urlPatterns = "/admin/editPatient")
public class EditPatientServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // fetching params from request
        Long patientID = Long.parseLong(request.getParameter("patientID"));
        // checking if such patient exists
        Users user = usersService.getOne(patientID);
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
            usersService.saveAndFlush(user);
            // update data in UserInfo
            UserInfo userInfo = userInfoService.getOne(patientID);
            userInfo.setFirstName(firstname);
            userInfo.setMiddleName(midname);
            userInfo.setLastName(lastname);
            userInfo.setDateOfBirth(dob);
            userInfo.setPhone(phone);
            userInfo.setEmail(email);
            userInfo.setAddress(address);
            userInfoService.saveAndFlush(userInfo);
            // give admin notification about successful update operation
            HttpSession session = request.getSession();
            ControllerUtils.giveTicketToMyMessage(session, "Patient's info successfully updated!");
            response.sendRedirect("/admin/patients");
        } else {
            HttpSession session = request.getSession();
            ControllerUtils.giveTicketToMyMessage(session, "User with such id don't exist anymore");
            response.sendRedirect("/admin/patients");
        }
    }

}
