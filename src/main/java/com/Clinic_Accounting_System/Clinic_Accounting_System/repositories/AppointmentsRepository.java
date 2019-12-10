package com.Clinic_Accounting_System.Clinic_Accounting_System.repositories;

import com.Clinic_Accounting_System.Clinic_Accounting_System.models.AppointmentID;
import com.Clinic_Accounting_System.Clinic_Accounting_System.models.Appointments;
import com.Clinic_Accounting_System.Clinic_Accounting_System.models.Doctors;
import com.Clinic_Accounting_System.Clinic_Accounting_System.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;

@Repository
public interface AppointmentsRepository extends JpaRepository<Appointments, AppointmentID> {


    Long countAppointmentsByAppointmentID_Doctor_IdAndAppointmentID_Date(Long doctorID, Date selectedDate);

    // to fetch ArrayList of Appointments for patient by it's id
    ArrayList<Appointments> findAllByAppointmentID_Patient_Id(Long patientID);

    // to fetch ArrayList of Appointments for doctor by it's id
    ArrayList<Appointments> findAllByAppointmentID_Doctor_Id(Long doctorID);

    /*
        The real reason why i'm doing this without Date object is that my ass finally in the moon because i tried a lot ways how to process this query with Date object and didn't succeed
            i don't know why, but hibernate don't want to process any query with Date here. I even reviewed and applied some changes on DB models
            i really wanted to figure out the problem, but i don't have time to do this. So, if you know solution, let me know. Thanks!
            and if solution is obvious, don't hate me please, cuz now i'm sleeping only 4-5 hours.
            So, this approach has only one artifact: when docID, patientID and numberInQueue will be the same in one time, they all will be deleted
     */
    //
    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    void deleteByAppointmentID_DoctorAndAppointmentID_PatientAndAppointmentID_NumberInQueue(Doctors doctorID, UserInfo patientID, int numberInQueue);

    /* --> Deprecated code <--
        // throw this because i don't know how to get access to EmbeddedID Foreign Key
        @Query(countQuery = "SELECT COUNT(a) FROM Appointments AS a WHERE a.appointmentID.doctor.id = :doctorID AND a.appointmentID.date = :selectedDate")
        Long countAppointmentsToTheDoctorOnThisDate(@Param("doctorID") Long doctorID, @Param("selectedDate") Date selectedDate);
        //  not working because of Date in AppID
        // checking existence of the Appointments entity instance with such AppointmentID
        @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Appointments as a WHERE a.appointmentID = :appointmentID")
        boolean existsByAppointmentID(@Param("appointmentID") AppointmentID appointmentID);

        // same situation as in existsByAppointmentID
        @Transactional
        @Modifying(flushAutomatically = true, clearAutomatically = true)
        void deleteByAppointmentID(AppointmentID appointmentID);

        // needs session
        @Transactional
        @Modifying(flushAutomatically = true, clearAutomatically = true)
        @Query(value = "DELETE FROM appointments as a WHERE a.patient_id = :patient AND a.doctor_id = :doctor AND a.number_in_queue = :numberInQueue", nativeQuery = true)
        void customDeleteByAppointmentIDFields(@Param("patient") Long patient, @Param("doctor") Long doctor, @Param("numberInQueue") int numberInQueue);
    */
}
