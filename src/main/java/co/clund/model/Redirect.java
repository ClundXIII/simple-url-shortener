package co.clund.model;

import co.clund.model.db.DBRedirect;

import co.clund.model.db.DatabaseConnector;

public class Redirect {

	private final Long id;
	
	private String link;
	private String url;
	
	private final DatabaseConnector dbCon;
	private final DBRedirect dbRedirect;

	public Redirect(DBRedirect dbRedirect, DatabaseConnector dbCon){
		this.id = dbRedirect.getId();
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

	public String renderViewRow() {
		
		StringBuilder html = new StringBuilder();
		
		html.append("<tr><td>\"" + link + "\"</td><td>\"" + url + "\"</td>");
		html.append("<td>" + "all User, TBD" + "</td>");
		html.append("<td><form action=\"redirects\" method=\"get\">");
		html.append("<input type=\"hidden\" name=\"edit\" value=\"true\"/>");
		html.append("<input type=\"hidden\" name=\"id\" value=\"" + id + "\"/>");
		html.append("<input type=\"submit\" value=\"edit\"/>");
		html.append("</form></td>");
		
		return html.toString(); //<td>Delete</td></tr>";
	}
	
	public String renderEditForm() {

		StringBuilder html = new StringBuilder();
		
		html.append("<form action=\"action\" method=\"post\">");
		html.append("<input type=\"hidden\" name=\"f\" value=\"edit_redirect\"/>");
		html.append("<input type=\"hidden\" name=\"id\" value=\"" + id + "\"/>");
		html.append("<input name=\"link\" value=\"" + link + "\"/>");
		html.append("<input name=\"url\" value=\"" + url + "\"/>");
		html.append("<input type=\"submit\" value=\"edit\"/>");
		html.append("</form></hl>");
		
		return html.toString(); //<td>Delete</td></tr>";
	}
}
