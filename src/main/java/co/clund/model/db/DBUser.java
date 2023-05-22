package co.clund.model.db;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import co.clund.model.User;

@Entity
@Table( name = "user" )
public class DBUser {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Basic
	private Long id;
	
	@Basic
	private String username;

	@Basic
	private String password;
	
	@Basic
	private Boolean is_admin;

	DBUser(){
	}

	DBUser(String username, String password, boolean is_admin) {
		this(username, password, new Boolean(is_admin));
	}

	DBUser(String username, String password, Boolean is_admin) {
		this.username = username;
		this.password = password;
		this.is_admin = is_admin;
	}
	
	public DBUser(User user) {
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.is_admin = user.getIs_admin();
		this.id = user.getId();
	}

	public Long getId() {
		return this.id;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public Boolean getIs_admin() {
		return is_admin;
	}

}
