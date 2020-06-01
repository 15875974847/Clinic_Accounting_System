package com.Clinic_Accounting_System.dao.UserRH;

import com.Clinic_Accounting_System.executor.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IsUserExistsHandler implements ResultHandler<Boolean> {

    private static IsUserExistsHandler instance;

    private IsUserExistsHandler() {}

    public static synchronized IsUserExistsHandler getInstance() {
        if (instance == null)
            instance = new IsUserExistsHandler();
        return instance;
    }

    @Override
    public Boolean handle(ResultSet resultSet) throws SQLException {
        return resultSet.next();
    }

}
