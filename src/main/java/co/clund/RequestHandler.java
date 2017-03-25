package co.clund;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class RequestHandler extends AbstractHandler {
	
	private static final String ADMIN_PATH = "/admin/";

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		System.out.println("target: " + target);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html;charset=utf-8");
		
        try {
			if (target.startsWith(ADMIN_PATH)) {
				
			}
			else {
				
			}
        }
        catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(e.getMessage());
            e.printStackTrace();
        }
		
        baseRequest.setHandled(true);
        response.getWriter().println("<h1>Hello World</h1>");
	}

}
