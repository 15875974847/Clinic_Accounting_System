package com.Clinic_Accounting_System.servlets.patient.see_doctor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@WebServlet(name = "PatientsSeeDoctorPageServlet", urlPatterns = "/patient/see_doctor")
public class SeeDoctorPageServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        // making some calls to retrieve info from database
        List<Doctors> doctors = doctorsService.findAll();
        // setting list of doctors as request attribute
        request.setAttribute("doctors", doctors);
        // pass this page thru message-only-with-ticket system
        ControllerUtils.goThru_MessageByTicket_System(session);
        request.getRequestDispatcher("patient/see_doctor.jsp").forward(request, response);
    }

}
