package co.clund.model.db;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "redirect" )
public class DBRedirect {

	@Id
	@Basic
	private Long id;
	
	@Basic
	private String link;
	
	@Basic
	private String url;
	
	public DBRedirect(){
		
	}

}
