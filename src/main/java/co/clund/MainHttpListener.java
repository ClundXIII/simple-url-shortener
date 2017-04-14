package co.clund;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.session.DefaultSessionCache;
import org.eclipse.jetty.server.session.NullSessionDataStore;
import org.eclipse.jetty.server.session.SessionCache;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import org.json.*;

import co.clund.model.db.DatabaseConnector;

public class MainHttpListener {

	private static final String JSON_VARNAME_IP = "ip";
	private static final String JSON_VARNAME_PORTS = "ports";
	private static final String JSON_VARNAME_LISTEN = "listen";

	private class ListenInfo {
		private final String ip;
		private final int[] ports;

		public ListenInfo(String ip, int[] ports) {
			this.ip = ip;
			this.ports = ports;
		}

		public void setupServer(Server server) {
			for (int port : ports) {
				@SuppressWarnings("resource")
				ServerConnector connector = new ServerConnector(server, 1, 1);
				connector.setHost(ip);
				connector.setPort(port);
				server.addConnector(connector);
			}
		}
	}

	private List<ListenInfo> lInfo = new ArrayList<>();

	private final AbstractHandler reqHandler;

	private final Server server;
	private final DatabaseConnector dbCon;

	public MainHttpListener(BufferedReader r) {

		StringBuilder sb = new StringBuilder();

		try {
			while (r.ready()) {
				sb.append(r.readLine() + "\n");
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		JSONObject jO = new JSONObject(sb.toString());

		try {

			JSONObject dbConfig = jO.getJSONObject("persistence-db-config");

			this.dbCon = new DatabaseConnector(dbConfig.getString("persistence-unit-name"), dbConfig.getString("url"),
					dbConfig.getString("username"), dbConfig.getString("password"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		reqHandler = new RequestHandler(this.dbCon);

		server = new Server(new QueuedThreadPool(512, 1));

		JSONArray jsonListeners = jO.getJSONArray(JSON_VARNAME_LISTEN);

		for (int i = 0; i < jsonListeners.length(); i++) {
			JSONObject jListener = jsonListeners.getJSONObject(i);

			String ip = jListener.getString(JSON_VARNAME_IP);

			int ports[] = new int[128];

			JSONArray jPorts = jListener.getJSONArray(JSON_VARNAME_PORTS);

			for (int j = 0; j < jPorts.length(); j++) {
				ports[j] = jPorts.getInt(j);
				System.out.println("Initializing Listener at " + ip + ":" + ports[j]);
				for (ListenInfo l : lInfo) {
					l.setupServer(server);
				}
			}

			lInfo.add(new ListenInfo(ip, ports));

		}

		//server.setHandler(reqHandler);


        // Create a ServletContext, with a session handler enabled.
        ServletContextHandler context = new ServletContextHandler(
                ServletContextHandler.SESSIONS);
        //context.setContextPath("*");
        context.setResourceBase(".");
        context.setHandler(reqHandler);

        // Access the SessionHandler from the context.
        SessionHandler sessions = context.getSessionHandler();
        
        // Explicitly set Session Cache and null Datastore.
        // This is normally done by default,
        // but is done explicitly here for demonstration.
        // If more than one context is to be deployed, it is
        // simpler to use SessionCacheFactory and/or
        // SessionDataStoreFactory instances set as beans on 
        // the server.
        SessionCache cache = new DefaultSessionCache(sessions);
        cache.setSessionDataStore(new NullSessionDataStore());
        sessions.setSessionCache(cache);

        // Servlet to read/set the greeting stored in the session.
        // Can be accessed using http://localhost:8080/hello
        //context.addServlet(BasicSessionServlet.class, "/");

        server.setHandler(context);
        
	}

	public void run() {
		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void stop_join() {

		try {
			server.stop();

			server.join();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
