package co.clund.model;

import java.util.List;

import co.clund.model.db.DBUser;
import co.clund.model.db.DatabaseConnector;

public class User {
	
	private DatabaseConnector dbCon;

	private Long id;
	
	private String username;
	private String password;
	private Boolean is_admin;
	
	
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

	public User(DBUser dbUser, DatabaseConnector dbCon) {
		this(dbUser.getId(), dbUser.getUsername(), dbUser.getPassword(), dbCon);
	}

	private User(Long id, String username, String password, DatabaseConnector dbCon) {
		
		this.id = id;
		
		this.username = username;
		this.password = password;
		
		this.dbCon = dbCon;
	}
	
	public static User createNew(String username, String password, DatabaseConnector dbCon){
		
		User u = new User(null, username, password, dbCon);
		u.persist();
		
		return u;
	}

	public void persist() {
		dbCon.persist(this);
	}

	public Boolean getIs_admin() {
		return is_admin;
	}

	public void setIs_admin(Boolean is_admin) {
		this.is_admin = is_admin;
		persist();
	}
	
	public List<Redirect> getRedirects(){
		return dbCon.getRedirectsByUser(id);
	}
	
}
