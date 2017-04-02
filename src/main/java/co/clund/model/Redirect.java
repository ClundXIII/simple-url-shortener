package co.clund.model;

import co.clund.model.db.DBRedirect;

import co.clund.model.db.DatabaseConnector;

public class Redirect {

	private String link;
	private String url;
	
	public Redirect(DBRedirect dbRedirect, DatabaseConnector dbCon){
		link = dbRedirect.getLink();
		url = dbRedirect.getUrl();
	}

	public String getUrl() {
		return url;
	}
}
