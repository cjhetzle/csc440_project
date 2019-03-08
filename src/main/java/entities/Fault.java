package main.java.entities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.java.backend.Utils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Fault implements Entity {
	
	@Override
	public void load() throws SQLException {
        throw new NotImplementedException();
	}

	@Override
	public void save() throws SQLException {
        throw new NotImplementedException();
	}

	@Override
	public void insert() throws SQLException {
        throw new NotImplementedException();
	}

	public static List<String> getAllFaults() throws SQLException {
		List<String> faults = new ArrayList<String>();
		List<Map<String,String>> tuples = Utils.select("SELECT * FROM Fault;", new String[0]);
		for (Map<String,String> tuple : tuples) {
			faults.add(tuple.get("name"));
		}
		return faults;
	}

}
