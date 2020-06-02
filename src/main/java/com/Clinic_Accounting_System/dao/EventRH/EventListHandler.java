package com.Clinic_Accounting_System.dao.EventRH;

import com.Clinic_Accounting_System.entities.Event;
import com.Clinic_Accounting_System.executor.ResultHandler;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventListHandler implements ResultHandler<List<Event>> {

    private static EventListHandler instance;

    private EventListHandler() {
    }

    public static synchronized EventListHandler getInstance() {
        if (instance == null)
            instance = new EventListHandler();
        return instance;
    }

    @Override
    public List<Event> handle(ResultSet resultSet) throws SQLException {
        final List<Event> eventList = new ArrayList<>();
        while (resultSet.next()) {
            final Long id = resultSet.getLong("id");
            final String header = resultSet.getString("header");
            final String content = resultSet.getString("content");
            final Date startDate = resultSet.getDate("start_date");
            final Date endDate = resultSet.getDate("end_date");
            final Boolean onlyForPersonal = resultSet.getBoolean("only_for_personal");
            eventList.add(new Event(id, header,content, startDate, endDate, onlyForPersonal));
        }
        return eventList;
    }

}
