package com.Clinic_Accounting_System.servlets.admin.appointments;

import com.Clinic_Accounting_System.utils.ControllerUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminsAppointmentsPageServlet", urlPatterns = "/admin/appointments")
public class AppointmentsPageServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // fetching from database all appointments
        List<Appointments> appointments = appointmentsService.findAll();
        // push them into request scope, yeah, i know it's bad idea, but i don't have time to implement something better
        request.setAttribute("appointments", appointments);
        HttpSession session = request.getSession();
        ControllerUtils.goThru_MessageByTicket_System(session);
        request.getRequestDispatcher("admin/appointments.jsp").forward(request, response);
    }

}
