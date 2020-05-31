package com.Clinic_Accounting_System.Clinic_Accounting_System;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	// For producing a deployable war file, we have to provide a SpringBootServletInitializer subclass and override its configure method.
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ClinicAccountingSystemApplication.class);
	}

}
