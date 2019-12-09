package com.Clinic_Accounting_System.Clinic_Accounting_System.repositories;

import com.Clinic_Accounting_System.Clinic_Accounting_System.models.AppointmentID;
import com.Clinic_Accounting_System.Clinic_Accounting_System.models.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.ArrayList;

@Repository
public interface AppointmentsRepository extends JpaRepository<Appointments, AppointmentID> {
//    @Query(countQuery = "SELECT COUNT(a.appointmentID) FROM Appointments AS a WHERE a.appointmentID.doctor.id = :doctorID AND a.appointmentID.date = :selectedDate")
//    Long countAppointmentsToTheDoctorOnThisDate(@Param("doctorID") Long doctorID, @Param("selectedDate") Date selectedDate);

    // count number of appointments for doctor on selected date
    Long countAppointmentsByAppointmentID_Doctor_IdAndAppointmentID_Date(Long doctorID, Date selectedDate);

    // to fetch ArrayList of Appointments for patient by it's id
    ArrayList<Appointments> findAllByAppointmentID_Patient_Id(Long patientID);

    // to fetch ArrayList of Appointments for doctor by it's id
    ArrayList<Appointments> findAllByAppointmentID_Doctor_Id(Long doctorID);

    // checking existence of the Appointments entity instance with such AppointmentID
    boolean existsAppointmentsByAppointmentID(AppointmentID appointmentID);

    // deleting Appointments entity instance with such AppointmentID
    Long deleteAppointmentsByAppointmentID(AppointmentID appointmentID);
}
