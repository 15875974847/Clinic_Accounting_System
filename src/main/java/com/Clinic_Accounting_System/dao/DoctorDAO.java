package com.Clinic_Accounting_System.dao;

import com.Clinic_Accounting_System.dao.DoctorRH.DoctorHandler;
import com.Clinic_Accounting_System.dao.DoctorRH.DoctorListHandler;
import com.Clinic_Accounting_System.entities.Doctor;
import com.Clinic_Accounting_System.executor.Executor;
import com.Clinic_Accounting_System.executor.ResultHandler;

import java.sql.SQLException;
import java.util.List;

public class DoctorDAO {

    private static DoctorDAO instance;
    private final Executor executor = Executor.getInstance();
    private final ResultHandler<Doctor> doctorHandler = DoctorHandler.getInstance();
    private final ResultHandler<List<Doctor>> doctorListHandler = DoctorListHandler.getInstance();

    private DoctorDAO() {}

    public static synchronized DoctorDAO getInstance() {
        if (instance == null)
            instance = new DoctorDAO();
        return instance;
    }

    // DB scripts
    private static final String Get_Doctor_By_Id =
            "SELECT * FROM doctors WHERE id = ?";

    private static final String Create_Doctor =
            "INSERT INTO doctors(id, degree, specialization) VALUES(?,?,?)";

    private static final String Remove_By_Id =
            "DELETE FROM doctors WHERE id = ?";

    private static final String Get_All =
            "SELECT * FROM doctors";

    private static final String Update_Doctor_By_Id =
            "UPDATE doctors SET degree = ?, specialization = ? WHERE id = ?";

    public Doctor getDoctorById(Long id) throws SQLException {
        return executor.executeQuery(Get_Doctor_By_Id, doctorHandler, id);
    }

    public void createDoctor(Long id, String degree, String specialization) throws SQLException {
        executor.executeUpdate(Create_Doctor, id, degree, specialization);
    }

    public void removeById(Long id) throws SQLException {
        executor.executeUpdate(Remove_By_Id, id);
        // cascade removal from `staffEntity` table
        StaffEntityDAO.getInstance().removeById(id);
    }

    public List<Doctor> getAll() throws SQLException {
        return executor.executeQuery(Get_All, doctorListHandler);
    }

    public void updateDoctor(Long id, String degree, String specialization) throws SQLException {
        executor.executeUpdate(Update_Doctor_By_Id, degree, specialization, id);
    }

}
