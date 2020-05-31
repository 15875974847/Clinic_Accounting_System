package com.Clinic_Accounting_System.servlets.admin.events;

import com.Clinic_Accounting_System.utils.ControllerUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AdminsDeleteEventServlet", urlPatterns = "/admin/deleteEvent")
public class DeleteEventServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // gettin' eventID param from request
        Long eventID = Long.parseLong(request.getParameter("eventID"));
        // removing it from database
        eventsService.deleteById(eventID);
        // notifying about successful delete operation
        HttpSession session = request.getSession();
        ControllerUtils.giveTicketToMyMessage(session, "Event successfully deleted!");
        response.sendRedirect("/admin/events");
    }

}
