package com.Clinic_Accounting_System.servlets.admin.appointments;

import com.Clinic_Accounting_System.dao.AppointmentDAO;
import com.Clinic_Accounting_System.utils.ControllerUtils;
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
@WebServlet(name = "AdminsDeleteAllAppointmentsAfterDateServlet", urlPatterns = "/admin/deleteAllAppointmentsAfterDate")
public class DeleteAfterDateServlet extends HttpServlet {

    private final AppointmentDAO appointmentDAO = AppointmentDAO.getInstance();

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            // fetch date from request
            Date date = java.sql.Date.valueOf(request.getParameter("date"));
            ControllerUtils.makeCorrectionForTimeZone(date);
            // delete all appointments after date
            int numberOfDeletedAppointments = appointmentDAO.removeAppointmentsOlderThenDate(date);
            // give ticket to success message
            HttpSession session = request.getSession();
            ControllerUtils.giveTicketToMyMessage(session, numberOfDeletedAppointments + " appointments successfully deleted!");
            response.sendRedirect(request.getContextPath() + "/admin/appointments");
        } catch (SQLException e) {
            log.error("500: SQLException at admin/home/DeleteAfterDateServlet: " + e.getMessage());
            request.getRequestDispatcher("/pages/errors/500.html").forward(request, response);
        }
    }

}
