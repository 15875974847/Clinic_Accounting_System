package com.Clinic_Accounting_System.servlets.doctor.home;

import com.Clinic_Accounting_System.utils.ControllerUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "DoctorsAddEventServlet", urlPatterns = "/doctor/addNewEvent")
public class AddEventServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        // gettin' params from request
        String header = request.getParameter("header");
        String content = request.getParameter("content");
        Date startDate = java.sql.Date.valueOf(request.getParameter("start_date"));
        ControllerUtils.makeCorrectionForTimeZone(startDate);
        Date endDate = java.sql.Date.valueOf(request.getParameter("end_date"));
        ControllerUtils.makeCorrectionForTimeZone(endDate);
        boolean onlyForPersonal = request.getParameterValues("only_for_personal") != null;
        // creating object with invoked params
        Events event = new Events(header, content, startDate, endDate, onlyForPersonal);
        // and finally save object into db and flush
        eventsService.saveAndFlush(event);
        // give ticket to message notifying user that event successfully created
        ControllerUtils.giveTicketToMyMessage(session, "Event successfully created!");
        response.sendRedirect("/doctor/home");
    }

}
