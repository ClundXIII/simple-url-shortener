package co.clund.action;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.mindrot.jbcrypt.BCrypt;

import co.clund.model.User;
import co.clund.model.db.DatabaseConnector;

public class Login extends AbstractAction {

	private static final String WRONG_PASSWORD_MESSAGE = "wrong password!<br><a href=\"login\">try again</a>";

	public Login(DatabaseConnector dbCon) {
		super("login", dbCon);
	}

	@Override
	public String processAction(String target, Request baseRequest, HttpServletRequest request,
			HttpServletResponse response) {
		
		Map<String, String[]> getParams = baseRequest.getParameterMap();
		
		String[] usernames = getParams.get("username");
		String[] passwords = getParams.get("password");
		
		if ((usernames != null) && (usernames.length == 0)){
			return "error: no username";
		}
		if ((passwords != null) && (passwords.length == 0)){
			return "error: no password";
		}
		
		@SuppressWarnings("null")
		String username = usernames[0];
		@SuppressWarnings("null")
		String password = passwords[0];
		
		User userByName = dbCon.getUserByName(username);
		if (userByName == null){
			return WRONG_PASSWORD_MESSAGE;
		}
		String passwordToTest = userByName.getPassword();
		
		if (BCrypt.checkpw(password, passwordToTest)){
			request.getSession().setAttribute("userid", userByName.getId());
		}
		else {
			return WRONG_PASSWORD_MESSAGE;
		}
		
		try {
			response.sendRedirect("redirects");
		} catch (IOException e) {
			return "Error: " + e.getMessage();
		}
		
		return "logging in ...";
	}

}
