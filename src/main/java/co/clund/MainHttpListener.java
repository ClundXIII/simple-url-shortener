package co.clund;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.json.*;

public class MainHttpListener {

	private class ListenInfo {
		private final String ip;
		private final int[] ports;

		public ListenInfo(String ip, int[] ports) {
			this.ip = ip;
			this.ports = ports;
		}

		public void setupServer(Server server){
			for (int port : ports){
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

	public MainHttpListener(BufferedReader r) {

		reqHandler = new RequestHandler();
		
		server = new Server(new QueuedThreadPool(512, 1));
		
		StringBuilder sb = new StringBuilder();

		try {
			while (r.ready()) {
				sb.append(r.readLine() + "\n");
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		JSONObject jO = new JSONObject(sb.toString());

		JSONArray jsonListeners = jO.getJSONArray("listen");

		for (int i = 0; i < jsonListeners.length(); i++) {
			JSONObject jListener = jsonListeners.getJSONObject(i);

			String ip = jListener.getString("ip");

			int ports[] = new int[128];

			JSONArray jPorts = jListener.getJSONArray("ports");

			for (int j = 0; j < jPorts.length(); j++) {
				ports[j] = jPorts.getInt(j);
				System.out.println("Initializing Listener at " + ip + ":" + ports[j]);
				for (ListenInfo l : lInfo){
					l.setupServer(server);
				}
			}

			lInfo.add(new ListenInfo(ip, ports));

		}
		
		server.setHandler(reqHandler);
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
