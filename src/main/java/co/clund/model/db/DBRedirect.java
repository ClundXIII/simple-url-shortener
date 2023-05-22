package co.clund.model.db;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import co.clund.model.Redirect;

@Entity
@Table( name = "redirect" )
public class DBRedirect {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Basic
	private Long id;
	
	@Basic
	private String link;
	
	@Basic
	private String url;

	DBRedirect(){}

	DBRedirect(String link, String url){
		this.link = link;
		this.url = url;
	}
	
	DBRedirect(Redirect r){
		this.link = r.getLink();
		this.url = r.getUrl();
		this.id = r.getId();
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
