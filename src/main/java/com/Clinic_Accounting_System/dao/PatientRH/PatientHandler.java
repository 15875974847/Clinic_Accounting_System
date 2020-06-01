package com.Clinic_Accounting_System.dao.PatientRH;

import com.Clinic_Accounting_System.dao.UserDAO;
import com.Clinic_Accounting_System.entities.Patient;
import com.Clinic_Accounting_System.executor.ResultHandler;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientHandler implements ResultHandler<Patient> {

    private static PatientHandler instance;

    private PatientHandler() {}

    public static synchronized PatientHandler getInstance() {
        if (instance == null)
            instance = new PatientHandler();
        return instance;
    }

    @Override
    public Patient handle(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) return null;
        final long id = resultSet.getLong("id");
        final String firstname = resultSet.getString("first_name");
        final String lastname = resultSet.getString("last_name");
        final String middlename = resultSet.getString("middle_name");
        final Date dob = resultSet.getDate("dob");
        final String email = resultSet.getString("email");
        final String phone = resultSet.getString("phone");
        final String address = resultSet.getString("address");
        final String medicalHistory = resultSet.getString("medical_history");
        return new Patient(id, firstname, lastname, middlename, dob,
                            email, phone, address, medicalHistory,
                UserDAO.getInstance().getById(id));
    }

}
