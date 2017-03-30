package co.clund.model;

import java.util.HashMap;
import java.util.Map;

import co.clund.model.db.DBUser;
import co.clund.model.db.DatabaseConnector;

public class User {

	/**
	 * true = owner
	 * false = user
	 */
	private final Map<String, Boolean> redirects = new HashMap<>();
	
	public User(DBUser dbUser, DatabaseConnector dbCon) {
		this(dbUser.getUsername(), dbUser.getPassword(), dbCon);
	}

	public User(String username, String password, DatabaseConnector dbCon) {
		
	}

	public static User login(String username, String password, DatabaseConnector dbCon){
		return null;

		//db.User = 
		
		
	}
	
	
}
