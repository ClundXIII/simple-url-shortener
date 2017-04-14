package co.clund;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import co.clund.admin.MainAdminHandler;
import co.clund.model.db.DatabaseConnector;
import co.clund.redirect.RedirectHandler;

public class RequestHandler extends AbstractHandler {
	
	private static final String ADMIN_PATH = "/admin/";
	
	public static String getAdminPath() {
		return ADMIN_PATH;
	}

	private final DatabaseConnector dbCon;

	private final MainAdminHandler maHandler;

	private final RedirectHandler reHandler;

	public RequestHandler(DatabaseConnector dbCon){
		this.dbCon = dbCon;

		maHandler = new MainAdminHandler(this.dbCon);
		reHandler = new RedirectHandler(this.dbCon);
	}
	
	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		System.out.println("target: " + target);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html;charset=utf-8");
		
        try {
			if (target.startsWith(ADMIN_PATH)) {
				maHandler.handleGET(target, baseRequest, request, response);
			}
			else {
				reHandler.handle(target, response);
		        response.getWriter().println("<h1>Redirecting ...</h1>");
			}
        }
        catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Error:<br>");
            response.getWriter().println(e.getMessage());
            e.printStackTrace();
        }
	
        baseRequest.setHandled(true);
	}

}
