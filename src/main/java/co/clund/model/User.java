package co.clund.model;

import java.util.HashSet;
import java.util.Set;

import co.clund.model.db.DBUser;
import co.clund.model.db.DatabaseConnector;

public class User {
	
	private DatabaseConnector dbCon;

	private Long id;
	
	private String username;
	private String password;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
		persist();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
		persist();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		persist();
	}

	private final Set<Redirect> redirects = new HashSet<>();
	
	public User(DBUser dbUser, DatabaseConnector dbCon) {
		this(dbUser.getId(), dbUser.getUsername(), dbUser.getPassword(), dbCon);
	}

	public User(Long id, String username, String password, DatabaseConnector dbCon) {
		
		this.id = id;
		
		this.username = username;
		this.password = password;
		
		this.dbCon = dbCon;
	}

	public static User login(String username, String password, DatabaseConnector dbCon){
		return null;
		
		///TODO
	}

	public Set<Redirect> getRedirects() {
		return redirects;
	}

	public void persist() {
		dbCon.persist(new DBUser(id, username, password));
	}
	
}
