package com.Clinic_Accounting_System.dao.AppointmentRH;

import com.Clinic_Accounting_System.dao.DoctorDAO;
import com.Clinic_Accounting_System.dao.PatientDAO;
import com.Clinic_Accounting_System.entities.Appointment;
import com.Clinic_Accounting_System.executor.ResultHandler;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppointmentListHandler implements ResultHandler<List<Appointment>> {

    private static AppointmentListHandler instance;

    private AppointmentListHandler() {}

    public static synchronized AppointmentListHandler getInstance() {
        if (instance == null)
            instance = new AppointmentListHandler();
        return instance;
    }

    @Override
    public List<Appointment> handle(ResultSet resultSet) throws SQLException {
        final List<Appointment> appointmentList = new ArrayList<>();
        while (resultSet.next()) {
            final long doctor_id = resultSet.getLong("doctor_id");
            final long patient_id = resultSet.getLong("patient_id");
            final Date date = resultSet.getDate("date");
            final int number_in_queue = resultSet.getInt("number_in_queue");
            final String comment = resultSet.getString("comment");
            appointmentList.add(new Appointment(doctor_id, patient_id, date, number_in_queue, comment,
                    DoctorDAO.getInstance().getDoctorById(doctor_id),
                    PatientDAO.getInstance().getPatientById(patient_id)));
        }
        return appointmentList;
    }

}
