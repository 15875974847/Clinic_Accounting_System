package com.Clinic_Accounting_System.executor;


import com.Clinic_Accounting_System.db.JdbcConnector;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Executor {
    private static Executor instance;
    private static final DataSource mysqlDataSource = JdbcConnector.getDataSource();

    private Executor() {
    }

    public static synchronized Executor getInstance() {
        if (instance == null)
            instance = new Executor();
        return instance;
    }

    public void executeUpdate(final String update, Object... args) throws SQLException {
        try (final Connection con = mysqlDataSource.getConnection();
            final PreparedStatement stmt = con.prepareStatement(update)) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].getClass() == Integer.class) {
                    stmt.setInt(i + 1, (Integer) args[i]);
                } else {
                    stmt.setString(i + 1, (String) args[i]);
                }
            }
            stmt.executeUpdate();
        }
    }

    public <T> T executeQuery(final String query, final ResultHandler<T> handler, Object... args) throws SQLException {
        try (final Connection con = mysqlDataSource.getConnection();
             final PreparedStatement stmt = con.prepareStatement(query)) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].getClass() == Integer.class) {
                    stmt.setInt(i + 1, (Integer) args[i]);
                } else {
                    stmt.setString(i + 1, (String) args[i]);
                }
            }
            return handler.handle(stmt.executeQuery());
        }
    }
}
