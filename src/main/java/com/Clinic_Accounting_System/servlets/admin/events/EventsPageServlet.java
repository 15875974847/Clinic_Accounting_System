package com.Clinic_Accounting_System.servlets.admin.events;

import com.Clinic_Accounting_System.utils.ControllerUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminsEventsPageServlet", urlPatterns = "/admin/events")
public class EventsPageServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // making call to db to fetch all events
        List<Events> events = eventsService.findAll();
        request.setAttribute("events", events);
        // process thru message-by-ticket system
        HttpSession session = request.getSession();
        ControllerUtils.goThru_MessageByTicket_System(session);
        request.getRequestDispatcher("admin/events.jsp").forward(request, response);
    }

}
