package com.Clinic_Accounting_System.Clinic_Accounting_System.repositories;

import com.Clinic_Accounting_System.Clinic_Accounting_System.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
    Those guys will implement main methods to work with database
 */
@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Users findByUsernameAndPassword(String username, String password);

    boolean existsByUsername(String username);

    Users getByUsername(String username);
}
