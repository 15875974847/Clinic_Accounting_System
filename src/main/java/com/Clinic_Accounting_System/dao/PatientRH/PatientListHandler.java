package com.Clinic_Accounting_System.dao.PatientRH;

import com.Clinic_Accounting_System.dao.UserDAO;
import com.Clinic_Accounting_System.entities.Patient;
import com.Clinic_Accounting_System.executor.ResultHandler;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientListHandler implements ResultHandler<List<Patient>> {

    private static PatientListHandler instance;

    private PatientListHandler() {}

    public static synchronized PatientListHandler getInstance() {
        if (instance == null)
            instance = new PatientListHandler();
        return instance;
    }

    @Override
    public List<Patient> handle(ResultSet resultSet) throws SQLException {
        final List<Patient> patients = new ArrayList<>();
        while (resultSet.next()) {
            final long id = resultSet.getLong("id");
            final String firstname = resultSet.getString("first_name");
            final String lastname = resultSet.getString("last_name");
            final String middlename = resultSet.getString("middle_name");
            final Date dob = resultSet.getDate("dob");
            final String email = resultSet.getString("email");
            final String phone = resultSet.getString("phone");
            final String address = resultSet.getString("address");
            final String medicalHistory = resultSet.getString("medical_history");
            patients.add(new Patient(id, firstname, lastname, middlename, dob,
                    email, phone, address, medicalHistory,
                    UserDAO.getInstance().getById(id)));
        }
        return patients;
    }

}
