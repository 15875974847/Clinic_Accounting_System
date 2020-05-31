package com.Clinic_Accounting_System.servlets.doctor.find_patient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DoctorsFindPatientPageServlet", urlPatterns = "/doctor/find_patient")
public class FindPatientPageServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        // making call to db to fetch all patients
        List<UserInfo> patients = patientInfoService.findAll();
        // setting list of patients as a param
        request.setAttribute("patients", patients);
        // go thru message-by-ticket to display messages
        ControllerUtils.goThru_MessageByTicket_System(session);
        request.getRequestDispatcher("doctor/find_patient.jsp").forward(request, response);
    }

}