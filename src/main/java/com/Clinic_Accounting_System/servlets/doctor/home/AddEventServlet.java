package com.Clinic_Accounting_System.servlets.doctor.home;

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
import java.sql.Date;
import java.sql.SQLException;

@Log4j2
@WebServlet(name = "DoctorsAddEventServlet", urlPatterns = "/doctor/addNewEvent")
public class AddEventServlet extends HttpServlet {

    private final EventDAO eventDAO = EventDAO.getInstance();

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            // get params from request
            String header = request.getParameter("header");
            String content = request.getParameter("content");
            Date startDate = java.sql.Date.valueOf(request.getParameter("start_date"));
            ControllerUtils.makeCorrectionForTimeZone(startDate);
            Date endDate = java.sql.Date.valueOf(request.getParameter("end_date"));
            ControllerUtils.makeCorrectionForTimeZone(endDate);
            boolean onlyForPersonal = request.getParameterValues("only_for_personal") != null;

            // create new event
            eventDAO.createEvent(header, content, startDate, endDate, onlyForPersonal);

            // give ticket to message notifying user that event successfully created
            HttpSession session = request.getSession();
            ControllerUtils.giveTicketToMyMessage(session, "Event successfully created!");
            response.sendRedirect(request.getContextPath() + "/doctor/home");
        } catch (SQLException e) {
            log.error("500: SQLException at admin/appointments/AppointmentsPageServlet: " + e.getMessage());
            request.getRequestDispatcher("/pages/errors/500.jsp").forward(request, response);
        }
    }

}
