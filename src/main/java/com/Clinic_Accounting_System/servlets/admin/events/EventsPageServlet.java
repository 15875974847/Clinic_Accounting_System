package com.Clinic_Accounting_System.servlets.admin.events;

import com.Clinic_Accounting_System.dao.EventDAO;
import com.Clinic_Accounting_System.entities.Event;
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
@WebServlet(name = "AdminsEventsPageServlet", urlPatterns = "/admin/events")
public class EventsPageServlet extends HttpServlet {

    private final EventDAO eventDAO = EventDAO.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // making call to db to fetch all events
            List<Event> events = eventDAO.getAll();
            request.setAttribute("events", events);
            // process thru message-by-ticket system
            HttpSession session = request.getSession();
            ControllerUtils.goThru_MessageByTicket_System(session);
            request.getRequestDispatcher("admin/events.jsp").forward(request, response);
        } catch (SQLException e) {
            log.error("500: SQLException at admin/events/EventsPageServlet");
            request.getRequestDispatcher("errors/500.html").forward(request, response);
        }
    }

}
