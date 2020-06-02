package com.Clinic_Accounting_System.dao.StaffEntityRH;

import com.Clinic_Accounting_System.dao.UserDAO;
import com.Clinic_Accounting_System.entities.StaffEntity;
import com.Clinic_Accounting_System.executor.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffEntityHandler implements ResultHandler<StaffEntity> {

    private static StaffEntityHandler instance;

    private StaffEntityHandler() {}

    public static synchronized StaffEntityHandler getInstance() {
        if (instance == null)
            instance = new StaffEntityHandler();
        return instance;
    }

    @Override
    public StaffEntity handle(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) return null;
        final Long id = resultSet.getLong("id");
        final Double degree = resultSet.getDouble("salary");
        return new StaffEntity(id, degree,
                UserDAO.getInstance().getById(id));
    }

}
