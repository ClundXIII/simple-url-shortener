package co.clund.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.jetty.server.Request;
import org.reflections.Reflections;

import co.clund.action.AbstractAction;
import co.clund.model.db.DatabaseConnector;
import co.clund.util.ResourceUtil;

public class MainAdminHandler {

	private final DatabaseConnector dbCon;

	private final Map<String, AbstractAction> actions = new HashMap<>();

	public MainAdminHandler(DatabaseConnector dbCon) {
		this.dbCon = dbCon;

		Reflections reflections = new Reflections("co.clund.action");

		Set<Class<? extends AbstractAction>> allClasses = reflections.getSubTypesOf(AbstractAction.class);

		System.out.println("[ADMIN HANDLER] total Classes: " + allClasses.size());
		
		for (Class<? extends AbstractAction> c : allClasses) {
			if (!Modifier.isAbstract(c.getModifiers())) {
				Constructor<? extends AbstractAction> cons;
				try {
					cons = c.getConstructor(DatabaseConnector.class);
					AbstractAction s = cons.newInstance(this.dbCon);
					System.out.println("[ADMIN HANDLER] Adding action \"" + s.getFunction() + "\" from class \"" + s.toString() + "\"");
					actions.put(s.getFunction(), s);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	public void handleGET(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		System.out.println("admin: " + target.replace("/admin/", ""));

		HttpSession session = request.getSession();

		String location = target.replace("/admin/", "").replace("/admin", "");

		boolean do_nav = true;

		if ((session.getAttribute("userid") == null) && (!location.equals("action"))) {
			location = "login";
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
			session.invalidate();
			break;

		case "action":
			//actions.get(request.)
			break;

		case "login":
			body = ResourceUtil.getResourceAsString("/html/login.html");
			do_nav = false;
			break;

		default:
			body = "not found";
		}

		try (PrintWriter w = response.getWriter()) {

			String index = ResourceUtil.getResourceAsString("/html/index.html");

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
