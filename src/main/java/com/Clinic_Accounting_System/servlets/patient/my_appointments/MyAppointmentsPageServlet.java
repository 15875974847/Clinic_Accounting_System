package com.Clinic_Accounting_System.servlets.patient.my_appointments;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "PatientsMyAppointmentsPageServlet", urlPatterns = "/patient/my_appointments")
public class MyAppointmentsPageServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        // making some calls to database to retrieve necessary info
        UserInfo patientInfo = patientInfoService.getOne((Long)session.getAttribute("user_id"));
        if(patientInfo != null){
            // fetch from database all patient's
            List<Appointments> appointments = appointmentsService.findAllByAppointmentID_Patient_Id(patientInfo.getId());
            //set it as request attrib
            request.setAttribute("appointments", appointments);
            request.getRequestDispatcher("patient/my_appointments.jsp").forward(request, response);
        } else {
            return ControllerUtils.processNonexistentUserWithValidSessionParams(session, request);
        }
    }

}
