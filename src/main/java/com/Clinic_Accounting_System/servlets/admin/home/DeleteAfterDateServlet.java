package com.Clinic_Accounting_System.servlets.admin.home;

import com.Clinic_Accounting_System.utils.ControllerUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "AdminsDeleteAllAppointmentsAfterDateServlet", urlPatterns = "/admin/deleteAllAppointmentsAfterDate")
public class DeleteAfterDateServlet extends HttpServlet {

        public void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            // fetching date from request
            Date date = java.sql.Date.valueOf(request.getParameter("date"));
            ControllerUtils.makeCorrectionForTimeZone(date);
            // delete all appointments after date
            int numberOfDeletedAppointments = appointmentsService.removeOlderThan(date);
            // give ticket to success message
            HttpSession session = request.getSession();
            ControllerUtils.giveTicketToMyMessage(session, numberOfDeletedAppointments + " appointments successfully deleted!");
            response.sendRedirect("/admin/appointments");
        }

}
