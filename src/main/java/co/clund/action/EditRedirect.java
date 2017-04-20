package co.clund.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;

import co.clund.model.db.DatabaseConnector;

public class EditRedirect extends AbstractAction{

	public EditRedirect(DatabaseConnector dbCon) {
		super("edit_redirect", dbCon);
	}

	@Override
	public String processAction(String target, Request baseRequest, HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

}
