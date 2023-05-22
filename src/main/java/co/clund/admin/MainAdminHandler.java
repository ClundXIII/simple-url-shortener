package co.clund.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.jetty.server.Request;
import org.reflections.Reflections;

import co.clund.action.AbstractAction;
import co.clund.model.Redirect;
import co.clund.model.db.DatabaseConnector;
import co.clund.util.ResourceUtil;

public class MainAdminHandler {

	private static final String REDIRECT_BODY = "$$REDIRECT_BODY$$";
	private static final String REDIRECT_TABLE_BODY = "$$REDIRECT_TABLE_BODY$$";

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

		boolean doNav = true;

		if ((session.getAttribute("userid") == null) && (!location.equals("action"))) {
			location = "login";
		}

		String body = "";

		switch (location) {
		case "":
		case "redirects":
			body = ResourceUtil.getResourceAsString("/html/redirects.html");
			
			Map<String, String[]> m = request.getParameterMap();
			
			if (m.get("edit") == null){
			
				StringBuilder tableColumns = new StringBuilder();
				
				List<Redirect> rs = dbCon.getUserById((Long)session.getAttribute("userid")).getRedirects();
				for (Redirect r : rs){
					tableColumns.append(r.renderViewRow());
				}
				body = body.replace(REDIRECT_BODY, ResourceUtil.getResourceAsString("/html/redirects_table.html")).replace(REDIRECT_TABLE_BODY, tableColumns.toString());
			}
			else {
				String[] ids = m.get("id");
				System.out.println("ids: ");
				for (String s : ids){
					System.out.println(s);
				}
				String id = ids[0];
				Long idLong = Long.valueOf(id);
				Redirect redirectById = dbCon.getRedirectById(idLong);
				if (redirectById == null){
					body = body.replace(REDIRECT_BODY, "cannot find Redirect");
				}
				else {
					body = body.replace(REDIRECT_BODY, redirectById.renderEditForm() );
				}
			}
			break;

		case "user":
			body = "<h1>user management</h1>";
			break;

		case "logout":
			body = "logging out ...<br><a href=\"login\">back to login</a>";
			session.invalidate();
			doNav = false;
			break;

		case "action":
			doNav = false;
			String actionStrings[] = request.getParameterMap().get("f");
			
			if (actionStrings.length != 0){
				String actionString = actionStrings[0];
				
				AbstractAction thisA = actions.get(actionString);

				if (thisA != null){
					body = thisA.processAction(target, baseRequest, request, response);
				}
				else {
					body = "action \"" + request.getParameterMap().get("f") + "\" not found";
				}
				
			}
			else {
				body = "post parameter f not set";
			}
			break;

		case "login":
			body = ResourceUtil.getResourceAsString("/html/login.html");
			doNav = false;
			break;

		default:
			body = "not found";
		}

		try (PrintWriter w = response.getWriter()) {

			String index = ResourceUtil.getResourceAsString("/html/index.html");

			index = index.replace("$$BODY$$", body);
			if (doNav) {
				index = index.replace("$$NAVIGATION$$", ResourceUtil.getResourceAsString("/html/navigation.html"));
			} else {
				index = index.replace("$$NAVIGATION$$", "");
			}

			w.println(index);
		}

	}

}
