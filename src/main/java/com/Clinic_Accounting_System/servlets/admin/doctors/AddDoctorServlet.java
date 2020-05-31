package com.Clinic_Accounting_System.servlets.admin.doctors;

import com.Clinic_Accounting_System.utils.ControllerUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "AdminsAddDoctorServlet", urlPatterns = "/admin/addNewDoctor")
public class AddDoctorServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // scrapping all params from request
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
        String salary = request.getParameter("salary");
        String degree = request.getParameter("degree");
        String specialization = request.getParameter("specialization");
        // here supposed to be different checks, but i don't have time, so let's omit them
        // persisting data into database
        if(!usersService.existsByUsername(username)) {
            Users user = new Users(username, password, Roles.doctor);
            usersService.saveAndFlush(user);
            UserInfo userInfo = new UserInfo(firstname, midname, lastname,
                    dob, phone, email, address, user);
            userInfoService.saveAndFlush(userInfo);
            StaffEntity staffEntity = new StaffEntity(Double.parseDouble(salary), userInfo);
            staffEntityService.saveAndFlush(staffEntity);
            Doctors doctor = new Doctors(degree, specialization, staffEntity);
            doctorsService.saveAndFlush(doctor);
            // notifying administrator about successful add operation
            HttpSession session = request.getSession();
            ControllerUtils.giveTicketToMyMessage(session, "Doctor successfully added!");
            response.sendRedirect("/admin/doctors");
        } else {
            HttpSession session = request.getSession();
            ControllerUtils.giveTicketToMyMessage(session, "Doctor with such username already exists!");
            response.sendRedirect("/admin/doctors");
        }
    }
}
