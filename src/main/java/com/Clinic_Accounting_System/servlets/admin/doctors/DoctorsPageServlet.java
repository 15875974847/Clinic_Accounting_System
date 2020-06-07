package com.Clinic_Accounting_System.servlets.admin.doctors;

import com.Clinic_Accounting_System.dao.DoctorDAO;
import com.Clinic_Accounting_System.entities.Doctor;
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
import java.util.List;

@Log4j2
@WebServlet(name = "AdminsDoctorsPageServlet", urlPatterns = "/admin/doctors")
public class DoctorsPageServlet extends HttpServlet {

    private final DoctorDAO doctorDAO = DoctorDAO.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // making call to db to fetch all doctors
            List<Doctor> doctorList = doctorDAO.getAll();
            request.setAttribute("doctors", doctorList);
            // process thru message-by-ticket system
            HttpSession session = request.getSession();
            ControllerUtils.goThru_MessageByTicket_System(session);
            request.getRequestDispatcher("/pages/admin/doctors.jsp").forward(request, response);
        } catch (SQLException e) {
            log.error("500: SQLException at admin/doctors/DoctorsPageServlet");
            request.getRequestDispatcher("/pages/errors/500.jsp").forward(request, response);
        }
    }

}
