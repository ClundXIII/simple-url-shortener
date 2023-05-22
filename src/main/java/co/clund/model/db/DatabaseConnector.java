package co.clund.model.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import co.clund.model.User;
import co.clund.model.Redirect;

public class DatabaseConnector {

	private final EntityManagerFactory entityManagerFactory;

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public DatabaseConnector(String persistentUnitName, String url, String username, String password) {

		Map<String, String> configMap = new HashMap<>();

		if (url != null) {
			configMap.put("hibernate.connection.url", url);
		}
		if (username != null) {
			configMap.put("hibernate.connection.username", username);
		}
		if (password != null) {
			configMap.put("hibernate.connection.password", password);
		}

		entityManagerFactory = Persistence.createEntityManagerFactory(persistentUnitName, configMap);
	}

	public User getUserById(Long id) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		List<DBUser> result = entityManager
				.createQuery("SELECT * FROM DBUser WHERE DBUser.id LIKE :userid", DBUser.class)
				.setParameter("userid", id).setMaxResults(1).getResultList();

		DBUser dbUser = result.get(0);

		if (dbUser == null) {
			return null;
		}

		entityManager.getTransaction().commit();
		entityManager.close();
		entityManager.clear();

		return new User(dbUser, this);
	}

	public User getUserByName(String username) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		DBUser dbUser = entityManager
				.createQuery("select c from DBUser c where c.username like :uname", DBUser.class)
				.setParameter("uname", username).getSingleResult();

		if (dbUser == null) {
			return null;
		}

		entityManager.getTransaction().commit();
		entityManager.clear();
		entityManager.close();

		return new User(dbUser, this);
	}

	public DBUserRedirectRelation getRedirectByUserRedirectId(Long user_id, Long redirect_id) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		DBUserRedirectRelation dbRedir = entityManager
				.createQuery(
						"select c FROM DBUserRedirectRelation c WHERE c.user_id like :uid and "
								+ "c.redirect_id like :rid",
						DBUserRedirectRelation.class)
				.setParameter("uid", user_id).setParameter("rid", redirect_id).getSingleResult();

		entityManager.getTransaction().commit();
		entityManager.close();

		entityManager.clear();
		return dbRedir;
	}

	public Redirect getRedirectByLink(String link) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		DBRedirect dbRed = null;
		TypedQuery<DBRedirect> queryResult;
		try {
			queryResult = entityManager
					.createQuery("select c FROM DBRedirect c WHERE " + "c.link like :linkasdf ",
							DBRedirect.class)
					.setParameter("linkasdf", link);
			
			dbRed = queryResult.getSingleResult();
		} catch (Exception e) {
			System.out.println("no result: " + e.getMessage());
			return null;
		}
		

		entityManager.getTransaction().commit();
		entityManager.clear();
		entityManager.close();

		if (dbRed == null) {
			return null;
		}

		return new Redirect(dbRed, this);
	}

	public Redirect getRedirectById(Long id) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		List<DBRedirect> result = entityManager
				.createQuery("SELECT * FROM DBRedirect WHERE " + "DBRedirect.id LIKE :id ", DBRedirect.class)
				.setParameter("id", id).setMaxResults(1).getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();

		DBRedirect dbRed = result.get(0);

		if (dbRed == null) {
			return null;
		}
		entityManager.clear();
		return new Redirect(dbRed, this);
	}

	void persist(DBRedirect dbRedirect) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(dbRedirect);
		entityManager.getTransaction().commit();
		entityManager.clear();
	}

	void persist(DBUser dbUser) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(dbUser);
		entityManager.getTransaction().commit();
		entityManager.clear();
	}

	void persist(DBUserRedirectRelation dbRedir) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(dbRedir);
		entityManager.getTransaction().commit();
		entityManager.clear();
	}

	public List<Redirect> getRedirectsByUser(Long userid) {
		return null; // TODO: implement redirectsByUser.get(userid);
	}

	public void persist(Redirect redirect) {
		DBRedirect redirectToPersist = new DBRedirect(redirect);
		persist(redirectToPersist);
	}

	public void persist(User user) {
		DBUser redirectToPersist = new DBUser(user);
		persist(redirectToPersist);

	}

}
