package co.clund.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;

import co.clund.model.db.DatabaseConnector;

public class Login extends AbstractAction {

	public Login(DatabaseConnector dbCon) {
		super("login", dbCon);
	}

	@Override
	public String processAction(String target, Request baseRequest, HttpServletRequest request,
			HttpServletResponse response) {
		
		return "logging in ...";
	}

}
