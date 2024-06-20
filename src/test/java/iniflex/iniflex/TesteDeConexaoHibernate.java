package iniflex.iniflex;

import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TesteDeConexaoHibernate {
	private static EntityManagerFactory emf;

	@BeforeClass
	public static void setUp() {
		emf = Persistence.createEntityManagerFactory("iniflex");
	}

	@AfterClass
	public static void tearDown() {
		if (emf != null) {
			emf.close();
		}
	}

	@Test
	public void testConnection() {
		EntityManager em = emf.createEntityManager();
		assertNotNull("EntityManager não pode ser nulo: ", em);
		try {
			em.getTransaction().begin();
			em.createNativeQuery("SELECT 1").getSingleResult();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			assert false : "Falha no teste de conexão com o banco de dados.";
		} finally {
			em.close();
		}
	}
}
