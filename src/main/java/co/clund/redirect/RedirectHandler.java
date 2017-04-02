package co.clund.redirect;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import co.clund.model.Redirect;
import co.clund.model.db.DatabaseConnector;

public class RedirectHandler {

	private final DatabaseConnector dbCon;

	public RedirectHandler(DatabaseConnector dbCon) {
		this.dbCon = dbCon;
	}

	public void handle(String target, HttpServletResponse response) throws IOException {
		
		Redirect redirect = dbCon.getRedirectByLink(target.substring(1));
		if (redirect != null){
			response.sendRedirect(redirect.getUrl());
		}
		else {
			response.getWriter().println("searching for " + target.substring(1));
			response.getWriter().println("redir: redirect not found");
		}
	}

}
