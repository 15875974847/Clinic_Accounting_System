package com.Clinic_Accounting_System.servlets.admin.doctors;

import com.Clinic_Accounting_System.utils.ControllerUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AdminsDeleteDoctorServlet", urlPatterns = "/admin/deleteDoctor")
public class DeleteDoctorServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // gettin' docID param from request
        Long docID = Long.parseLong(request.getParameter("docID"));
        // if you want to know why i'm doing this -> go see AppointmentID gotcha
        appointmentsService.removeAllByAppointmentID_Doctor_Id(docID);
        // removing doc from database, cascade will do the rest with other doctor's entity dependencies
        doctorsService.deleteById(docID);
        // notifying about successful delete operation
        HttpSession session = request.getSession();
        ControllerUtils.giveTicketToMyMessage(session, "Doctor successfully deleted!");
        response.sendRedirect("/admin/doctors");
    }

}
