package com.Clinic_Accounting_System.servlets.patient.see_doctor;

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
@WebServlet(name = "PatientsSeeDoctorPageServlet", urlPatterns = "/patient/see_doctor")
public class SeeDoctorPageServlet extends HttpServlet {

    private final DoctorDAO doctorDAO = DoctorDAO.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            // fetch all doctors from db
            List<Doctor> doctors = doctorDAO.getAll();
            // set list of doctors as request attrib
            request.setAttribute("doctors", doctors);
            // pass this page thru message-only-with-ticket system
            ControllerUtils.goThru_MessageByTicket_System(session);
            request.getRequestDispatcher("patient/see_doctor.jsp").forward(request, response);
        } catch (SQLException e) {
            log.error("500: SQLException at patient/see_doctor/SeeDoctorPageServlet");
            request.getRequestDispatcher("errors/500.html").forward(request, response);
        }
    }

}
