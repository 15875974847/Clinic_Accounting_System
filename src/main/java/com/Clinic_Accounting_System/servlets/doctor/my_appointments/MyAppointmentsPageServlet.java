package com.Clinic_Accounting_System.servlets.doctor.my_appointments;

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
@WebServlet(name = "DoctorsMyAppointmentsPageServlet", urlPatterns = "/doctor/my_appointments")
public class MyAppointmentsPageServlet extends HttpServlet {

    private final AppointmentDAO appointmentDAO = AppointmentDAO.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            // get from session user_id param
            Long user_id = (Long)session.getAttribute("user_id");
            // fetch appointment's info from database
            List<Appointment> asPatientAppointments = appointmentDAO.getAppointmentsByPatientId(user_id);
            List<Appointment> asDoctorAppointments = appointmentDAO.getAppointmentsByDocId(user_id);
            // set those two lists as attributes of request scope
            request.setAttribute("asPatientAppointments", asPatientAppointments);
            request.setAttribute("asDoctorAppointments", asDoctorAppointments);
            // go thru message-by-ticket system
            ControllerUtils.goThru_MessageByTicket_System(session);
            request.getRequestDispatcher("doctor/my_appointments.jsp").forward(request, response);
        } catch (SQLException e) {
            log.error("500: SQLException at doctor/my_appointments/MyAppointmentsPageServlet");
            request.getRequestDispatcher("errors/500.html").forward(request, response);
        }
    }

}