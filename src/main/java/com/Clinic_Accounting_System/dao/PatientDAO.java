package com.Clinic_Accounting_System.dao;

import com.Clinic_Accounting_System.dao.PatientRH.PatientHandler;
import com.Clinic_Accounting_System.entities.Patient;
import com.Clinic_Accounting_System.executor.Executor;
import com.Clinic_Accounting_System.executor.ResultHandler;

import java.sql.Date;
import java.sql.SQLException;

public class PatientDAO {

    private static PatientDAO instance;
    private final Executor executor = Executor.getInstance();
    private ResultHandler<Patient> getPatientHandler = PatientHandler.getInstance();

    private PatientDAO() {}

    public static synchronized PatientDAO getInstance() {
        if (instance == null)
            instance = new PatientDAO();
        return instance;
    }

    // DB scripts
    private static final String Get_Patient_By_Id =
            "SELECT * FROM user_info WHERE id = ?";

    private static final String Update_Patient_MedicalHistory_By_Id =
            "UPDATE user_info SET medical_history = ? WHERE id = ?";

    private static final String Create_Patient =
            "INSERT INTO user_info(id, first_name, last_name, middle_name, dob," +
                    "email, phone, address, medical_history) VALUES(?,?,?,?,?,?,?,?,?)";

    public Patient getPatientById(long id) throws SQLException {
        return executor.executeQuery(Get_Patient_By_Id, getPatientHandler, id);
    }

    public void updatePatientMedicalHistoryById(long id, String medicalHistory) throws SQLException {
        executor.executeUpdate(Update_Patient_MedicalHistory_By_Id, medicalHistory, id);
    }

    public void createPatient(long id, String firstname, String lastname, String midname, Date dob,
                              String email, String phone, String address, String medHistory) throws SQLException {
        executor.executeUpdate(Create_Patient, id, firstname, lastname, midname,
                dob, email, phone, address, medHistory);
    }

}
