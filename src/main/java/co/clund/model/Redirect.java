package co.clund.model;

import co.clund.model.db.DBRedirect;

import co.clund.model.db.DatabaseConnector;

public class Redirect {

	private String link;
	private String url;
	
	private final DatabaseConnector dbCon;
	private final DBRedirect dbRedirect;

	public Redirect(DBRedirect dbRedirect, DatabaseConnector dbCon){
		this.link = dbRedirect.getLink();
		this.url = dbRedirect.getUrl();
		this.dbCon = dbCon;
		this.dbRedirect = dbRedirect;
	}

	public String getUrl() {
		return url;
	}

	public String getLink() {
		return link;
	}

	public void setUrl(String url) {
		this.url = url;

		persist();
	}

	public void setLink(String link) {
		this.link = link;

		persist();
	}
	
	public void persist() {
		dbCon.persist(new DBRedirect(dbRedirect.getId(), link, url));
	}
}
