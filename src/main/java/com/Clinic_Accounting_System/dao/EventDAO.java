package com.Clinic_Accounting_System.dao;

import com.Clinic_Accounting_System.dao.EventRH.EventListHandler;
import com.Clinic_Accounting_System.entities.Event;
import com.Clinic_Accounting_System.executor.Executor;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class EventDAO {

	private static EventDAO instance;
	private final Executor executor = Executor.getInstance();
	private final EventListHandler eventListHandler = EventListHandler.getInstance();

	private EventDAO() {}

	public static synchronized EventDAO getInstance() {
		if (instance == null)
			instance = new EventDAO();
		return instance;
	}

	private static final String Get_All =
			"SELECT * FROM `events`";

	private static final String Get_All_Events_By_OnlyForPersonal =
			"SELECT * FROM `events` WHERE only_for_personal = ?";

	private static final String Create_Event =
			"INSERT INTO `events`(header, content, start_date, end_date, only_for_personal) VALUES(?, ?, ?, ?, ?)";

	private static final String Remove_By_Id =
			"DELETE FROM `events` WHERE id = ?";

	public List<Event> getAll() throws SQLException {
		return executor.executeQuery(Get_All, eventListHandler);
	}

	public List<Event> getAllEventsByOnlyForPersonal(Boolean onlyForPersonal) throws SQLException {
		return executor.executeQuery(Get_All_Events_By_OnlyForPersonal, eventListHandler, onlyForPersonal);
	}

	public void createEvent(String header, String content, Date startDate, Date endDate, Boolean onlyForPersonal)
																							throws SQLException {
		executor.executeUpdate(Create_Event, header, content, startDate, endDate, onlyForPersonal);
	}

	public void removeEventById(Long id) throws SQLException {
		executor.executeUpdate(Remove_By_Id, id);
	}

}
