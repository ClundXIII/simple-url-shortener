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
	
}
