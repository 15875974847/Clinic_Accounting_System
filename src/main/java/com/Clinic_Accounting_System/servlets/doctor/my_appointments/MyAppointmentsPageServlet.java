package com.Clinic_Accounting_System.servlets.doctor.my_appointments;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DoctorsMyAppointmentsPageServlet", urlPatterns = "/doctor/my_appointments")
public class MyAppointmentsPageServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        // fetching from session user_id param
        Long user_id = (Long)session.getAttribute("user_id");
        // fetching appointment's info from database
        List<Appointments> asPatientAppointments = appointmentsService.findAllByAppointmentID_Patient_Id(user_id);
        List<Appointments> asDoctorAppointments = appointmentsService.findAllByAppointmentID_Doctor_Id(user_id);
        // set those two lists as attributes of request scope
        request.setAttribute("asPatientAppointments", asPatientAppointments);
        request.setAttribute("asDoctorAppointments", asDoctorAppointments);
        // go thru message-by-ticket system
        ControllerUtils.goThru_MessageByTicket_System(session);
        request.getRequestDispatcher("doctor/my_appointments.jsp").forward(request, response);
    }

}