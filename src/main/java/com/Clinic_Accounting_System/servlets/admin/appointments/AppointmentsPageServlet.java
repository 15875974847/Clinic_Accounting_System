package com.Clinic_Accounting_System.servlets.admin.appointments;

import com.Clinic_Accounting_System.dao.AppointmentDAO;
import com.Clinic_Accounting_System.entities.Appointment;
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
@WebServlet(name = "AdminsAppointmentsPageServlet", urlPatterns = "/admin/appointments")
public class AppointmentsPageServlet extends HttpServlet {

    private final AppointmentDAO appointmentDAO = AppointmentDAO.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // fetching from database all appointments
            List<Appointment> appointments = appointmentDAO.getAll();
            request.setAttribute("appointments", appointments);
            HttpSession session = request.getSession();
            // go thru message-by-ticket system
            ControllerUtils.goThru_MessageByTicket_System(session);
            request.getRequestDispatcher("/pages/admin/appointments.jsp").forward(request, response);
        } catch (SQLException e) {
            log.error("500: SQLException at admin/appointments/AppointmentsPageServlet: " + e.getMessage());
            request.getRequestDispatcher("/pages/errors/500.html").forward(request, response);
        }
    }

}
