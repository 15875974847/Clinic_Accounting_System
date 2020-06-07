package com.Clinic_Accounting_System.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.extern.log4j.Log4j2;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Log4j2
public class JdbcConnector {

    private static ComboPooledDataSource instance;

    private JdbcConnector() {}

    public static DataSource getDataSource() {
        if (instance == null) {
            synchronized (JdbcConnector.class) {
                try(InputStream resourceAsStream = JdbcConnector.class.getResourceAsStream("/jdbc.properties")) {
                    final Properties properties = new Properties();
                    properties.load(resourceAsStream);
                    instance = new ComboPooledDataSource();
                    instance.setDriverClass(properties.getProperty("driver"));
                    instance.setJdbcUrl(properties.getProperty("url"));
                    instance.setUser(properties.getProperty("username"));
                    instance.setPassword(properties.getProperty("password"));
                    instance.setMinPoolSize(2);
                    instance.setMaxPoolSize(40);
                } catch (PropertyVetoException | IOException e) {
                    log.error(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return instance;
    }

}
