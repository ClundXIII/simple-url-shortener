package co.clund;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import co.clund.model.db.DatabaseConnector;
import junit.framework.TestCase;

/**
 * Main class for testing
 */
public class AppTest extends TestCase {
	
	private final String tName;
	
	public AppTest(String testName) {
		super(testName);
		this.tName = testName;
	}

	public void testHttpServer() {
		System.out.println("starting test " + tName + " #1"); 
		try {
			InputStream res = getClass().getResourceAsStream("/apptest.json");
			InputStreamReader is = new InputStreamReader(res);
			BufferedReader br = new BufferedReader(is);
			MainHttpListener mhl = new MainHttpListener(br);
			mhl.run();
			
			Thread.sleep(1000);
			
			URL testUrl = new URL("http://127.0.0.1:8081");
	        URLConnection testCon = testUrl.openConnection();
	        BufferedReader in = new BufferedReader(
	                                new InputStreamReader(
	                                		testCon.getInputStream()));
	        String inputLine;

	        while ((inputLine = in.readLine()) != null) 
	            System.out.println(inputLine);
	        in.close();
			
			
			Thread.sleep(1000);
			
			mhl.stop_join();
			
			assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	

	public void testDbCon() {
		System.out.println("starting test " + tName + " #2");
		try {
			
			DatabaseConnector dbCon = new DatabaseConnector("co.clund.test", null, null, null, null); //$NON-NLS-1$
			
			System.out.println(dbCon.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
