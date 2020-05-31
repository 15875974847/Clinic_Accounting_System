package com.Clinic_Accounting_System.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;

public class JdbcConnector {
    private static ComboPooledDataSource instance;

    private JdbcConnector() {
    }

    public static DataSource getDataSource() {
        if (instance == null) {
            synchronized (JdbcConnector.class) {
                try {
                    final String url = "jdbc:mysql://localhost:3306/clinic?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false";
                    final String userName = "ClinicAdmin";
                    final String password = "pass";
                    instance = new ComboPooledDataSource();
                    instance.setDriverClass("com.mysql.jdbc.Driver");
                    instance.setJdbcUrl(url);
                    instance.setUser(userName);
                    instance.setPassword(password);
                    instance.setMinPoolSize(4);
                    instance.setMaxPoolSize(40);
                } catch (PropertyVetoException e) {
                    e.printStackTrace();
                }
            }
        }
        return instance;
    }

}
