package com.Clinic_Accounting_System.servlets.admin.patients;

import com.Clinic_Accounting_System.utils.ControllerUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AdminsDeletePatientServlet", urlPatterns = "/admin/deletePatient")
public class DeletePatientServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // fetching patientID from request
        Long patientID = Long.parseLong(request.getParameter("patientID"));
        UserInfo patient = userInfoService.getOne(patientID);
        if(patient != null){
            // if you want to know why i'm doing this -> go see AppointmentID gotcha
            appointmentsService.removeAllByAppointmentID_Patient_Id(patientID);
            // deleting from UserInfo table, cascade will delete from Users
            userInfoService.deleteById(patientID);
            // notifying about successful delete operation
            HttpSession session = request.getSession();
            ControllerUtils.giveTicketToMyMessage(session, "Patient successfully deleted!");
            response.sendRedirect("/admin/patients");
        } else {
            HttpSession session = request.getSession();
            ControllerUtils.giveTicketToMyMessage(session, "Such patient do not exist anymore!");
            response.sendRedirect("/admin/patients");
        }
    }

}
