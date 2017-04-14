package co.clund;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.session.SessionHandler;
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
		
		ContextHandler c = new ContextHandler("/");
		c.setContextPath("/");
		SessionHandler s = new SessionHandler();
		s.setHandler(reqHandler);
		c.setHandler(s);

		ContextHandlerCollection contexts = new ContextHandlerCollection();
		contexts.setHandlers(new Handler[]{c});
		server.setHandler(contexts);
        
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
