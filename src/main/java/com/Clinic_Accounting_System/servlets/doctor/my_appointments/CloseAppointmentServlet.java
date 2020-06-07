package com.Clinic_Accounting_System.servlets.doctor.my_appointments;

import com.Clinic_Accounting_System.dao.AppointmentDAO;
import com.Clinic_Accounting_System.dao.DoctorDAO;
import com.Clinic_Accounting_System.dao.PatientDAO;
import com.Clinic_Accounting_System.entities.Doctor;
import com.Clinic_Accounting_System.entities.Patient;
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
@WebServlet(name = "DoctorsCloseAppointmentServlet", urlPatterns = "/doctor/closeAppointmentAndLeaveANote")
public class CloseAppointmentServlet extends HttpServlet {

    private final PatientDAO patientDAO = PatientDAO.getInstance();
    private final DoctorDAO doctorDAO = DoctorDAO.getInstance();
    private final AppointmentDAO appointmentDAO = AppointmentDAO.getInstance();

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();

            // scrapping needed params from post request scope and session
            Long doctorID = (Long)session.getAttribute("user_id");
            Long patientID = Long.parseLong(request.getParameter("patientID"));
            Date date = java.sql.Date.valueOf(request.getParameter("date"));
            int numberInQueue = Integer.parseInt(request.getParameter("numberInQueue"));
            String note = request.getParameter("note");

            // make call to database to check if patient and doctor with such ids still existing there
            Patient patient = patientDAO.getPatientById(patientID);
            Doctor doctor = doctorDAO.getDoctorById(doctorID);
            if(patient != null && doctor != null){
                // delete Appointment instance by Appointment PK fields
                appointmentDAO.removeAppointmentByPK(doctor.getId(), patient.getId(), numberInQueue, date);
                // adding note to client's medical history if note exists
                if(!note.equals("")){
                    patient.setMedicalHistory(patient.getMedicalHistory() + "\n"+ "|" + note + "|");
                    patientDAO.updatePatientMedicalHistoryById(patient.getId(), patient.getMedicalHistory());
                }
                // give ticket to message about successful deleting and updating patient's information
                ControllerUtils.giveTicketToMyMessage(session, "Appointment successfully closed!");
            } else {
                ControllerUtils.giveTicketToMyMessage(session, "Patient and/or doctor do not exist anymore!");
            }
            response.sendRedirect(request.getContextPath() + "/doctor/my_appointments");
        } catch (SQLException e) {
            log.error("500: SQLException at doctor/my_appointments/CloseAppointmentServlet: " + e.getMessage());
            request.getRequestDispatcher("/pages/errors/500.jsp").forward(request, response);
        }
    }

}