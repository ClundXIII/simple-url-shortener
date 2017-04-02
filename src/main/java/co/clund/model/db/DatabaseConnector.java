package co.clund.model.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import co.clund.model.User;
import co.clund.model.Redirect;

public class DatabaseConnector {

	private EntityManagerFactory entityManagerFactory;

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	private final Map<Long, DBUser> userMap = new HashMap<>();
	private final Map<String, DBRedirect> redirectLinkMap = new HashMap<>();

	public DatabaseConnector(String persistentUnitName, String host, String username, String password) {
		this(persistentUnitName, host, username, password, true);
	}

	public DatabaseConnector(String persistentUnitName, String url, String username, String password,
			boolean loadInitialData) {

		Map<String, String> configMap = new HashMap<>();
		
		if (url != null){
			configMap.put("hibernate.connection.url", url);
		}
		if (username != null){
			configMap.put("hibernate.connection.username", username);
		}
		if (password != null){
			configMap.put("hibernate.connection.password", password);
		}

		entityManagerFactory = Persistence.createEntityManagerFactory(persistentUnitName, configMap);
		

		EntityManager entityManager = entityManagerFactory.createEntityManager();

		if (loadInitialData) {
			entityManager.getTransaction().begin();
			List<DBUser> result = entityManager.createQuery( "from DBUser", DBUser.class ).getResultList();
		
			System.out.println("adding users:");
			for (DBUser u : result){
				System.out.println("added user " + u.getUsername());
				userMap.put(u.getId(), u);
			}
			
			List<DBRedirect> result2 = entityManager.createQuery( "from DBRedirect", DBRedirect.class ).getResultList();

			System.out.println("adding redirs:");
			for (DBRedirect r : result2){
				System.out.println("adding redir " + r.getLink() + " " + r.getUrl());
				redirectLinkMap.put(r.getLink(), r);
			}
			
	        entityManager.getTransaction().commit();
	        entityManager.close();
		}
	}

	public User getUserById(Integer id) {

		return new User(userMap.get(id), this);
	}

	public Redirect getRedirectByLink(String link) {

		DBRedirect dbRed = redirectLinkMap.get(link);

		if (dbRed == null) {
			return null;
		}
		return new Redirect(dbRed, this);
	}

}
