package com.Clinic_Accounting_System.executor;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultHandler<T> {
    T handle(final ResultSet resultSet) throws SQLException;
}
