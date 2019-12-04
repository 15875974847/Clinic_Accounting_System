package com.Clinic_Accounting_System.Clinic_Accounting_System;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// includes Component, ComponentScan and AutoConfiguration annotations
@SpringBootApplication
// Specifying path to repositories
@EnableJpaRepositories("com.Clinic_Accounting_System.Clinic_Accounting_System.repositories")
public class ClinicAccountingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClinicAccountingSystemApplication.class, args);
	}

}
