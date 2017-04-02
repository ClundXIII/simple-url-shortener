package co.clund.admin;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;

import co.clund.model.db.DatabaseConnector;

public class MainAdminHandler {

	private final DatabaseConnector dbCon;

	public MainAdminHandler(DatabaseConnector dbCon) {
		this.dbCon = dbCon;
	}

	public void handleGET(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("admin: " + target.replace("/admin/", ""));
		response.getWriter().println("admin: " + target);
	}

}
