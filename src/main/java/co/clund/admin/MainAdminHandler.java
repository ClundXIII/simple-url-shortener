package co.clund.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.jetty.server.Request;

import co.clund.model.db.DatabaseConnector;
import co.clund.util.ResourceUtil;

public class MainAdminHandler {

	private final DatabaseConnector dbCon;

	public MainAdminHandler(DatabaseConnector dbCon) {
		this.dbCon = dbCon;
	}

	public void handleGET(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		System.out.println("admin: " + target.replace("/admin/", ""));
		
		HttpSession session = request.getSession();

		String location = target.replace("/admin/", "").replace("/admin", "");

		boolean do_nav = true;
		
		if (session.getAttribute("userid") == null) {
			location = "login";
			do_nav = false;
		}

		String body = "";

		switch (location) {
		case "":
		case "redirects":
			body = "<h1>redirect management</h1>";
			break;

		case "user":
			body = "<h1>user management</h1>";
			break;

		case "logout":
			body = "logging out ...";
			break;
			
		case "login":
			body = ResourceUtil.getResourceAsString("/html/login.html");
			break;

		default:
			body = "not found";
		}

		try (PrintWriter w = response.getWriter()) {

			String index = ResourceUtil.getResourceAsString("/html/index.html");

			index = index.replace("$$LOGIN$$", "login here<br>");
			index = index.replace("$$BODY$$", body);
			if (do_nav) {
				index = index.replace("$$NAVIGATION$$", ResourceUtil.getResourceAsString("/html/navigation.html"));
			} else {
				index = index.replace("$$NAVIGATION$$", "");
			}

			w.println(index);
		}

	}

}
