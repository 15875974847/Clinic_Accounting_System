package com.Clinic_Accounting_System.dao;

import com.Clinic_Accounting_System.dao.PatientRH.PatientHandler;
import com.Clinic_Accounting_System.dao.PatientRH.PatientListHandler;
import com.Clinic_Accounting_System.entities.Patient;
import com.Clinic_Accounting_System.executor.Executor;
import com.Clinic_Accounting_System.executor.ResultHandler;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class PatientDAO {

    private static PatientDAO instance;
    private final Executor executor = Executor.getInstance();
    private final ResultHandler<Patient> patientHandler = PatientHandler.getInstance();
    private final ResultHandler<List<Patient>> patientListHandler = PatientListHandler.getInstance();

    private PatientDAO() {}

    public static synchronized PatientDAO getInstance() {
        if (instance == null)
            instance = new PatientDAO();
        return instance;
    }

    // DB scripts
    private static final String Get_All =
            "SELECT * FROM user_info";

    private static final String Get_Patient_By_Id =
            "SELECT * FROM user_info WHERE id = ?";

    private static final String Create_Patient =
            "INSERT INTO user_info(id, first_name, last_name, middle_name, dob," +
                    "email, phone, address, medical_history) VALUES(?,?,?,?,?,?,?,?,?)";

    private static final String Remove_Patient =
            "DELETE FROM user_info WHERE id = ?";

    private static final String Update_All_Patient_Info_Except_MedHistory =
            "UPDATE user_info SET first_name = ?, last_name = ?, middle_name = ?," +
                    "dob = ?, email = ?, phone = ?, address = ?" +
                    "WHERE id = ?";

    private static final String Update_Patient_MedicalHistory_By_Id =
            "UPDATE user_info SET medical_history = ? WHERE id = ?";

    public List<Patient> getAll() throws SQLException {
        return executor.executeQuery(Get_All, patientListHandler);
    }

    public Patient getPatientById(Long id) throws SQLException {
        return executor.executeQuery(Get_Patient_By_Id, patientHandler, id);
    }

    public void createPatient(Long id, String firstname, String lastname, String midname, Date dob,
                              String email, String phone, String address, String medHistory) throws SQLException {
        executor.executeUpdate(Create_Patient, id, firstname, lastname, midname,
                dob, email, phone, address, medHistory);
    }

    public void removePatient(Long id) throws SQLException {
        executor.executeUpdate(Remove_Patient, id);
        // cascade removal from `users` table
        UserDAO.getInstance().removeById(id);
    }

    public void updateAllPatientInfoExceptMedHistory(Long id, String firstname, String lastname, String midname, Date dob,
                                String email, String phone, String address) throws SQLException {
        executor.executeUpdate(Update_All_Patient_Info_Except_MedHistory, firstname, lastname, midname,
                                    dob, email, phone, address, id);
    }

    public void updatePatientMedicalHistoryById(Long id, String medicalHistory) throws SQLException {
        executor.executeUpdate(Update_Patient_MedicalHistory_By_Id, medicalHistory, id);
    }

}
