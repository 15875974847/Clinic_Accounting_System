package com.Clinic_Accounting_System.servlets.doctor.personal_info;

import com.Clinic_Accounting_System.dao.DoctorDAO;
import com.Clinic_Accounting_System.entities.Doctor;
import com.Clinic_Accounting_System.utils.ControllerUtils;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@Log4j2
@WebServlet(name = "DoctorsPersonalInfoPageServlet", urlPatterns = "/doctor/pers_info")
public class PersonalInfoPageServlet extends HttpServlet {

    private final DoctorDAO doctorDAO = DoctorDAO.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            HttpSession session = request.getSession();
            // fetch data from db
            Doctor doctor = doctorDAO.getDoctorById((Long)session.getAttribute("user_id"));
            if(doctor != null){
                // push doctor into request scope
                request.setAttribute("doctor", doctor);
                // go thought message-by-ticket system
                ControllerUtils.goThru_MessageByTicket_System(session);
                request.getRequestDispatcher("/pages/doctor/pers_info.jsp").forward(request, response);
            } else {
                ControllerUtils.processNonexistentUserWithValidSessionParams(session, request, response);
            }
        } catch (SQLException e) {
            log.error("500: SQLException at doctor/personal_info/PersonalInfoPageServlet: " + e.getMessage());
            request.getRequestDispatcher("/pages/errors/500.html").forward(request, response);
        }
    }

}