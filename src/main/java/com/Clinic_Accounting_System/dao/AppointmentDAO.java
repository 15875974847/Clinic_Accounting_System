package com.Clinic_Accounting_System.dao;

import com.Clinic_Accounting_System.dao.AppointmentRH.AppointmentCountHandler;
import com.Clinic_Accounting_System.dao.AppointmentRH.AppointmentListHandler;
import com.Clinic_Accounting_System.entities.Appointment;
import com.Clinic_Accounting_System.executor.Executor;
import com.Clinic_Accounting_System.executor.ResultHandler;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class AppointmentDAO {

    private static AppointmentDAO instance;
    private final Executor executor = Executor.getInstance();
    private ResultHandler<Integer> appointmentCountHandler = AppointmentCountHandler.getInstance();
	private ResultHandler<List<Appointment>> appointmentListHandler = AppointmentListHandler.getInstance();

	private AppointmentDAO() {}

	public static synchronized AppointmentDAO getInstance() {
		if (instance == null)
			instance = new AppointmentDAO();
		return instance;
	}

    // DB scripts
	private static final String Get_All_Appointments =
			"SELECT * FROM appointments";
	
	private static final String Get_Appointments_By_PatientId = 
			"SELECT * FROM appointments WHERE patient_id = ?";
	
	private static final String Get_Appointments_By_DoctorId =
			"SELECT * FROM appointments WHERE doctor_id = ?";

	private static final String Count_Appointments_By_DoctorID_And_Date =
			"SELECT count(*) as count FROM appointments WHERE doctor_id = ? AND `date`= ?";

	private static final String Create_Appointment =
            "INSERT INTO appointments(doctor_id, patient_id, number_in_queue, date, comment) VALUES(?, ?, ?, ?, ?)";

	private static final String Remove_Appointments_OlderThen_Date =
			"DELETE FROM appointments WHERE `date` < ?";
	
	private static final String Remove_All_Appointments_By_PatientId =
			"DELETE FROM appointments WHERE patient_id = ?";
	
	private static final String Remove_All_Appointments_By_DoctorId =
			"DELETE FROM appointments WHERE doctor_id = ?";
	
	private static final String Remove_Appointment_By_DoctorId_And_PatientId_And_NumberInQueue_And_Date =
	"DELETE FROM appointments WHERE doctor_id = ? AND patient_id = ? AND number_in_queue = ? AND `date` = ?";

    public List<Appointment> getAll() throws SQLException {
    	return executor.executeQuery(Get_All_Appointments, appointmentListHandler);
	}

	public List<Appointment> getAppointmentsByPatientId(long patient_id) throws SQLException {
    	return executor.executeQuery(Get_Appointments_By_PatientId, appointmentListHandler, patient_id);
	}

	public List<Appointment> getAppointmentsByDocId(long doctor_id) throws SQLException {
		return executor.executeQuery(Get_Appointments_By_DoctorId, appointmentListHandler, doctor_id);
	}

	public Integer countAppointmentsByDocIdAndDate(long doctor_id, Date date) throws SQLException {
		return executor.executeQuery(Count_Appointments_By_DoctorID_And_Date, appointmentCountHandler, doctor_id, date);
	}

	public void createAppointment(long doctor_id, long patient_id, int number_in_queue, Date date, String comment) throws SQLException {
        executor.executeUpdate(Create_Appointment, doctor_id, patient_id, number_in_queue, date, comment);
    }

	public void removeAppointmentsOlderThenDate(Date date) throws SQLException {
    	executor.executeUpdate(Remove_Appointments_OlderThen_Date, date);
	}

	public void removeAllAppointmentsByPatientId(long patient_id) throws SQLException {
		executor.executeUpdate(Remove_All_Appointments_By_PatientId, patient_id);
	}

	public void removeAppointmentsByDocId(long doctor_id) throws SQLException {
		executor.executeUpdate(Remove_All_Appointments_By_DoctorId, doctor_id);
	}

	public void removeAppointmentsByPK(long doctor_id, long patient_id, int number_in_queue, Date date) throws SQLException {
		executor.executeUpdate(Remove_Appointment_By_DoctorId_And_PatientId_And_NumberInQueue_And_Date,
				doctor_id, patient_id, number_in_queue, date);
	}

}
