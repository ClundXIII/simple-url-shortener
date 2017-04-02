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

	public DBRedirect(Long id, String link, String url){
		this.id = id;
		this.link = link;
		this.url = url;
	}

	public Long getId() {
		return id;
	}

	public String getLink() {
		return link;
	}

	public String getUrl() {
		return url;
	}

}
