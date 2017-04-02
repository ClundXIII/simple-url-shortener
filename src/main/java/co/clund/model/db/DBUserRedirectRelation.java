package co.clund.model.db;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "user_redirect_relation" )
public class DBUserRedirectRelation {

	@Id
	@Basic
	private Long id;
	
	@Basic
	private Long user_id;
	
	@Basic
	private Long redirect_id;

	public DBUserRedirectRelation(){
		
	}

	public DBUserRedirectRelation(Long id, Long user_id, Long redirect_id){
		this.id = id;
		this.user_id = user_id;
		this.redirect_id = redirect_id;
	}

	public Long getId() {
		return id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public Long getRedirect_id() {
		return redirect_id;
	}
	
}
