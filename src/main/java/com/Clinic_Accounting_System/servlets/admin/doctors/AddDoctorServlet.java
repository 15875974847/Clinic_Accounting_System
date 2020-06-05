package com.Clinic_Accounting_System.servlets.admin.doctors;

import com.Clinic_Accounting_System.dao.DoctorDAO;
import com.Clinic_Accounting_System.dao.PatientDAO;
import com.Clinic_Accounting_System.dao.StaffEntityDAO;
import com.Clinic_Accounting_System.dao.UserDAO;
import com.Clinic_Accounting_System.entities.User;
import com.Clinic_Accounting_System.utils.ControllerUtils;
import com.Clinic_Accounting_System.utils.Roles;
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
@WebServlet(name = "AdminsAddDoctorServlet", urlPatterns = "/admin/addNewDoctor")
public class AddDoctorServlet extends HttpServlet {

    private final UserDAO userDAO = UserDAO.getInstance();
    private final PatientDAO patientDAO = PatientDAO.getInstance();
    private final StaffEntityDAO staffEntityDAO = StaffEntityDAO.getInstance();
    private final DoctorDAO doctorDAO = DoctorDAO.getInstance();

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
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
            String degree = request.getParameter("degree");
            String specialization = request.getParameter("specialization");
            Double salary = 0.0;
            try {
                salary = Double.parseDouble(request.getParameter("salary"));
            } catch (NullPointerException | NumberFormatException e){
                ControllerUtils.giveTicketToMyMessage(session, "Wrong `salary` field!");
                response.sendRedirect(request.getContextPath() + "/admin/doctors");
                return;
            }
            // persisting data into database
            if(!userDAO.existsByUsername(username)) {
                userDAO.createUser(username, password, Roles.doctor.name());
                final User user = userDAO.getByUsername(username);
                patientDAO.createPatient(user.getId(), firstname, lastname, midname, dob,
                                        email, phone, address, "");
                staffEntityDAO.createStaffEntity(user.getId(), salary);
                doctorDAO.createDoctor(user.getId(), degree, specialization);
                // notifying administrator about successful operation
                ControllerUtils.giveTicketToMyMessage(session, "Doctor successfully added!");
            } else {
                ControllerUtils.giveTicketToMyMessage(session, "Doctor with such username already exists!");
            }
            response.sendRedirect(request.getContextPath() + "/admin/doctors");
        } catch (SQLException e) {
            log.error("500: SQLException at admin/doctors/AddDoctorServlet: " + e.getMessage());
            request.getRequestDispatcher("/pages/errors/500.html").forward(request, response);
        }
    }
}
