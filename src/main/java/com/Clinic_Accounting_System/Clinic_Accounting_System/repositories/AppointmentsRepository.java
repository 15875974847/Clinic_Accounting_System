package com.Clinic_Accounting_System.Clinic_Accounting_System.repositories;

import com.Clinic_Accounting_System.Clinic_Accounting_System.models.AppointmentID;
import com.Clinic_Accounting_System.Clinic_Accounting_System.models.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;

public interface AppointmentsRepository extends JpaRepository<Appointments, AppointmentID> {
    @Query(countQuery = "SELECT COUNT(a) FROM appointments AS a WHERE a.doctor_id = :doctorID AND a.date = :selectedDate")
    Long countAppointmentsToTheDoctorOnThisDate(@Param("doctorID") Long doctorID, @Param("selectedDate") Date selectedDate);
}
