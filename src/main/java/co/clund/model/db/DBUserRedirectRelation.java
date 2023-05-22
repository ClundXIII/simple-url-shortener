package co.clund.model.db;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_redirect_relation")
public class DBUserRedirectRelation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic
	private Long id;

	@Basic
	private Long user_id;

	@Basic
	private Long redirect_id;

	DBUserRedirectRelation() {

	}

	DBUserRedirectRelation(Long user_id, Long redirect_id) {
		this.user_id = user_id;
		this.redirect_id = redirect_id;
	}

	public static DBUserRedirectRelation addUserRedirectRelation(DatabaseConnector dbCon, Long user_id,
			Long redirect_id) {
		
		DBUserRedirectRelation dbRedir = new DBUserRedirectRelation(user_id, redirect_id);
		
		dbCon.persist(dbRedir);

		return dbCon.getRedirectByUserRedirectId(user_id, redirect_id);
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
