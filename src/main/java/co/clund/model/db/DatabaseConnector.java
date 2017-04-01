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

	private final Map<Integer, DBUser> userMap = new HashMap<>();
	private final Map<String, DBRedirect> redirectLinkMap = new HashMap<>();

	public DatabaseConnector(String persistentUnitName, String host, String username, String password,
			String database){
		this(persistentUnitName, host, username, password,
				database, true);
	}
	
	public DatabaseConnector(String persistentUnitName, String host, String username, String password,
			String database, boolean loadInitialData) {

		/*Configuration configuration = new Configuration();
		configuration.addPackage("models").addAnnotatedClass(DBUser.class);
		configuration.addPackage("models").addAnnotatedClass(DBRedirect.class);
		configuration.addPackage("models").addAnnotatedClass(DBUserRedirectRelation.class);
		//configuration.configure("/META-INF/persistence.xml");
		configuration.configure("/META-INF/hibernate.cfg.xml");
		entityManagerFactory = configuration.buildSessionFactory();*/
		
		entityManagerFactory = Persistence.createEntityManagerFactory(persistentUnitName);

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		if (loadInitialData){
			//entityManager.getTransaction().begin();
	        //List<DBUser> result = entityManager.createQuery( "from user", DBUser.class ).getResultList();
			///TODO: add a loader for initial data
		}
	}

	public User getUserById(Integer id){
		
		return new User(userMap.get(id), this);
	}
	
	public Redirect getRedirectByLink(String link){
		
		return new Redirect(redirectLinkMap.get(link), this);
	}

}
