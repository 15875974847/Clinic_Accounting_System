package com.Clinic_Accounting_System.dao;

import com.Clinic_Accounting_System.dao.DoctorRH.DoctorHandler;
import com.Clinic_Accounting_System.entities.Doctor;
import com.Clinic_Accounting_System.executor.Executor;
import com.Clinic_Accounting_System.executor.ResultHandler;

import java.sql.SQLException;

public class DoctorDAO {

    private static DoctorDAO instance;
    private final Executor executor = Executor.getInstance();
    private ResultHandler<Doctor> getPatientHandler = DoctorHandler.getInstance();

    private DoctorDAO() {}

    public static synchronized DoctorDAO getInstance() {
        if (instance == null)
            instance = new DoctorDAO();
        return instance;
    }

    // DB scripts
    private static final String Get_Doctor_By_Id =
            "SELECT * FROM doctors WHERE id = ?";

    public Doctor getDoctorById(long id) throws SQLException {
        return executor.executeQuery(Get_Doctor_By_Id, getPatientHandler, id);
    }

}
