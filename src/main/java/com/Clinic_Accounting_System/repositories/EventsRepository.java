package com.Clinic_Accounting_System.Clinic_Accounting_System.repositories;

import com.Clinic_Accounting_System.Clinic_Accounting_System.models.Events;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;


public interface EventsRepository extends JpaRepository<Events, Long> {
    ArrayList<Events> findAllByOnlyForPersonal(boolean only_for_personal);
}
