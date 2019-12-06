package com.Clinic_Accounting_System.Clinic_Accounting_System;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


// includes Component, ComponentScan and AutoConfiguration annotations
@SpringBootApplication
// Specifying path to repositories
@EnableJpaRepositories("com.Clinic_Accounting_System.Clinic_Accounting_System.repositories")
public class ClinicAccountingSystemApplication extends SpringBootServletInitializer {

	// to have access to resources folder in jsp
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ClinicAccountingSystemApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(ClinicAccountingSystemApplication.class, args);
	}

}
