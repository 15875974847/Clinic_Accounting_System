package com.Clinic_Accounting_System.servlets.admin.patients;

import com.Clinic_Accounting_System.utils.ControllerUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminsPatientsPageServlet", urlPatterns = "/admin/patients")
public class PatientsPageServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // fetching patients from database
        List<UserInfo> patients = userInfoService.findAll();
        // setting this list of patients as request attrib
        request.setAttribute("patients", patients);
        HttpSession session = request.getSession();
        ControllerUtils.goThru_MessageByTicket_System(session);
        request.getRequestDispatcher("admin/patients.jsp").forward(request, response);
    }

}
