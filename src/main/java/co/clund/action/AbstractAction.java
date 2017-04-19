package co.clund.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;

import co.clund.model.db.DatabaseConnector;

public abstract class AbstractAction {
	
	protected final String function;
	protected final DatabaseConnector dbCon;

	public AbstractAction(String function, DatabaseConnector dbCon){
		this.function = function;
		this.dbCon = dbCon;
	}
	
	public abstract String processAction(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response);

	public String getFunction() {
		return function;
	}
	
}
