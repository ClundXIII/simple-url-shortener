package co.clund.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;

import co.clund.model.db.DatabaseConnector;

public class DeletRedirect extends AbstractAction {

	public DeletRedirect(DatabaseConnector dbCon) {
		super("delete_redirect", dbCon);
	}

	@Override
	public String processAction(String target, Request baseRequest, HttpServletRequest request,
			HttpServletResponse response) {
		return null;
	}

}
