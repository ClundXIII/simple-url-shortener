package co.clund;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.*;

public class MainHttpListener {

	private class ListenInfo {
		private final String ip;
		private final int[] ports;

		ListenInfo(String ip, int[] ports) {
			this.ip = ip;
			this.ports = ports;
		}

		public String getIp() {
			return ip;
		}

		public int[] getPorts() {
			return ports;
		}
	}

	private List<ListenInfo> lInfo = new ArrayList<>();

	public MainHttpListener(BufferedReader r) {

		StringBuilder sb = new StringBuilder();

		try {
			while (r.ready()) {
				sb.append(r.readLine());
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
			}

			lInfo.add(new ListenInfo(ip, ports));

		}
	}

	public void run() {
		// TODO Auto-generated method stub

	}

	public void join() {
		// TODO Auto-generated method stub

	}

}
