package co.clund.model.db;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "user" )
public class DBUser {

	@Id
	@Basic
	private Long id;
	
	@Basic
	private String username;
	
	@Basic
	private String password;

	DBUser(){
	}
	
	/*public User(Long id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}*/

	public Long getId() {
		return this.id;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

}
