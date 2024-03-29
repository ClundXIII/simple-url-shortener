package co.clund;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.persistence.EntityManager;

import org.mindrot.jbcrypt.BCrypt;

import co.clund.admin.MainAdminHandler;
import co.clund.model.Redirect;
import co.clund.model.User;
import co.clund.model.db.DBUser;
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
		InputStream res = getClass().getResourceAsStream("/apptest.json");

		URL testUrl;
		URLConnection testCon = null;
		MainHttpListener mhl = null;
		
		
		try (InputStreamReader is = new InputStreamReader(res);
				BufferedReader br = new BufferedReader(is);){
			mhl = new MainHttpListener(br);
			
			mhl.run();
			Thread.sleep(1000);
			
			testUrl = new URL("http://127.0.0.1:8081");
			testCon = testUrl.openConnection();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		

		try (BufferedReader in = new BufferedReader(new InputStreamReader(testCon.getInputStream()));) {


			Thread.sleep(1000);

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

			DatabaseConnector dbCon = new DatabaseConnector("co.clund.test", null, null, null);

			System.out.println(dbCon.toString());

			User u1 = User.createNew("user1", "pwd1", dbCon);
			User u2 = User.createNew("user2", "pwd2", dbCon);
			
			Redirect r1 = Redirect.createNew("link", "url", dbCon);
			
			EntityManager entityManager = dbCon.getEntityManagerFactory().createEntityManager();
			entityManager.getTransaction().begin();
			
			// now lets pull events from the database and list them
	        List<DBUser> result = entityManager.createQuery( "from DBUser", DBUser.class ).getResultList();
			for ( DBUser user : result ) {
				System.out.println( "User (" + user.getUsername() + ") : " + user.getPassword() );
			}
	        entityManager.getTransaction().commit();
	        
	        User u = dbCon.getUserByName("user1");
	        System.out.println("User u1 " + u.getUsername() + " : " + u.getPassword());
	        Redirect r2 = dbCon.getRedirectByLink("link");
	        System.out.println("Redirect r2 " + r2.getLink() + " : " + r2.getUrl());

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static void testMainAdminHandler(){
		
		MainAdminHandler a = new MainAdminHandler(null);
		a.toString();
		
	}
	
	public static void testBcrypt(){
		System.out.println("generating Test Password:");
		System.out.println(BCrypt.hashpw("testLogin", BCrypt.gensalt()));
	}
}
