package com.Clinic_Accounting_System.servlets.admin.events;

import com.Clinic_Accounting_System.dao.EventDAO;
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
@WebServlet(name = "AdminsDeleteEventServlet", urlPatterns = "/admin/deleteEvent")
public class DeleteEventServlet extends HttpServlet {

    private final EventDAO eventDAO = EventDAO.getInstance();

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // gettin' eventID param from request
            Long eventID = Long.parseLong(request.getParameter("eventID"));
            // remove event from database
            eventDAO.removeEventById(eventID);
            // notify about successful delete operation
            HttpSession session = request.getSession();
            ControllerUtils.giveTicketToMyMessage(session, "Event successfully deleted!");
            response.sendRedirect("/admin/events");
        } catch (SQLException e) {
            log.error("500: SQLException at admin/events/DeleteEventServlet");
            response.sendRedirect("/errors/500.html");
        }
    }

}
