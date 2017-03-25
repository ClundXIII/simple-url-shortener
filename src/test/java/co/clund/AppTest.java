package co.clund;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import junit.framework.TestCase;

/**
 * Main class for testing
 */
public class AppTest extends TestCase {
	
	public AppTest(String testName) {
		super(testName);
	}

	public void testApp() {
		try {
			InputStream res = getClass().getResourceAsStream("/apptest.json");
			InputStreamReader is = new InputStreamReader(res);
			BufferedReader br = new BufferedReader(is);
			MainHttpListener mhl = new MainHttpListener(br);
			mhl.run();
			assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
