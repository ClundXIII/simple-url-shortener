package co.clund.db;

import java.util.HashMap;
import java.util.Map;

public class DatabaseConnector {

	private final String host;
	private final String username;
	private final String password;
	private final String database;
	
	private final Map<Integer, User> userMap = new HashMap<>();
	private final Map<Integer, Redirect> redirectMap = new HashMap<>();
	
	public DatabaseConnector(String host, String username, String password, String database) {
		this.host = host;
		this.username = username;
		this.password = password;
		this.database = database;
	}
	
}
