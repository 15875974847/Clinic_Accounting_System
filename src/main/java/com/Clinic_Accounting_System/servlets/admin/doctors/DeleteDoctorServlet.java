package com.Clinic_Accounting_System.servlets.admin.doctors;

import com.Clinic_Accounting_System.dao.AppointmentDAO;
import com.Clinic_Accounting_System.dao.DoctorDAO;
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

@Log4j2
@WebServlet(name = "AdminsDeleteDoctorServlet", urlPatterns = "/admin/deleteDoctor")
public class DeleteDoctorServlet extends HttpServlet {

    private final AppointmentDAO appointmentDAO = AppointmentDAO.getInstance();
    private final DoctorDAO doctorDAO = DoctorDAO.getInstance();

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // get docID param from request
            Long docID = Long.parseLong(request.getParameter("docID"));
            // remove all doc's appointments
            appointmentDAO.getAppointmentsByDocId(docID);
            // remove doc from database, cascade will do the rest with other doctor's dependencies
            doctorDAO.removeById(docID);
            // notify about successful delete operation
            HttpSession session = request.getSession();
            ControllerUtils.giveTicketToMyMessage(session, "Doctor successfully deleted from database!");
            response.sendRedirect(request.getContextPath() + "/admin/doctors");
        } catch (SQLException e) {
            log.error("500: SQLException at admin/doctors/deleteDoctorServlet: " + e.getMessage());
            request.getRequestDispatcher("/pages/errors/500.html").forward(request, response);
        }
    }

}
