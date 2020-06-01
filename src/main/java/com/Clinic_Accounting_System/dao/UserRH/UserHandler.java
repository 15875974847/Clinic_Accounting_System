package com.Clinic_Accounting_System.dao.UserRH;

import com.Clinic_Accounting_System.entities.User;
import com.Clinic_Accounting_System.executor.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserHandler implements ResultHandler<User> {

    private static UserHandler instance;

    private UserHandler() {}

    public static synchronized UserHandler getInstance() {
        if (instance == null)
            instance = new UserHandler();
        return instance;
    }

    @Override
    public User handle(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) return null;
        final long id = resultSet.getLong("id");
        final String username = resultSet.getString("username");
        final String password = resultSet.getString("password");
        final String role = resultSet.getString("role");
        return new User(id, username, password, role);
    }

}
