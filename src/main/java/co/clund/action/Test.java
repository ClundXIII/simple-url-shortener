package co.clund.action;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;

import co.clund.model.db.DatabaseConnector;

public class Test extends AbstractAction {

	public Test(DatabaseConnector dbCon) {
		super("test", dbCon);
	}

	@Override
	public String processAction(String target, Request baseRequest, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, String[]> getParams = baseRequest.getParameterMap();
		
		System.out.println("GET params: ");
		for (Entry<String, String[]> e : getParams.entrySet()){
			System.out.println(e.getKey() + ":" + e.getValue()[0]);
		}
		System.out.println("=END=");
		
		return "testing, 1, 2, ...";
	}

}
