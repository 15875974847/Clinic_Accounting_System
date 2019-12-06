package com.Clinic_Accounting_System.Clinic_Accounting_System.repositories;

import com.Clinic_Accounting_System.Clinic_Accounting_System.models.AppointmentID;
import com.Clinic_Accounting_System.Clinic_Accounting_System.models.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentsRepository extends JpaRepository<Appointments, AppointmentID> {

}
