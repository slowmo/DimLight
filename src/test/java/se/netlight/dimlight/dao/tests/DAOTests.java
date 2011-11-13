package se.netlight.dimlight.dao.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import se.netlight.dimlight.dao.DAOException;
import se.netlight.dimlight.dao.StupidDimlightDAO;
import se.netlight.dimlight.objects.Statement;
import se.netlight.dimlight.objects.User;
import se.netlight.dimlight.utils.jdbc.AutoInstantiatingDatabaseSource;

public class DAOTests {
	private static StupidDimlightDAO dao;

	@BeforeClass
	public static void setupTest() {
		System.out.println("Initializing HSQL database");
		
		dao = new StupidDimlightDAO();
		AutoInstantiatingDatabaseSource datasource = new AutoInstantiatingDatabaseSource();
		datasource.setDriverClassName("org.hsqldb.jdbcDriver");
		datasource.setUrl("jdbc:hsqldb:mem:testdb");
		datasource.setPreparationFile("testdb.script");
		dao.setDatasource(datasource);

		System.out.println("Checking connection");
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.queryForObject("SELECT 1 FROM User WHERE id=1", Integer.class);
		
		System.out.println("Init completed... commencing tests"); 
	}

	@Test
	public void testUserLoading() throws DAOException {
		User u = dao.getUserForId(1);
		assertEquals(u.getName(), "Mo");
		assertTrue(u.isAdmin());
		
		User u2 = dao.getUserForName("Mo");
		assertEquals(u.getEMail(), u2.getEMail());
		
		List<User> users = dao.getAllUsers();
		assertEquals(u, users.get(0));
		assertFalse(users.get(2).isAdmin());
	}
	
	public void testUserStoring() throws DAOException {
		User u = new User("Testuser", "password", "secret", 100.0, "testemail");
		dao.saveUser(u);	
		assertTrue(u.getId() > 0);
		
		User u2 = dao.getUserForName("Testuser");
		assertTrue(u.getId() == u2.getId());
		assertEquals(u, u2);
		assertEquals(u.getEMail(), u2.getEMail());
		
		u2.setEMail("testemail2");
		dao.saveUser(u);
		
		User u3 = dao.getUserForId(u.getId());
		assertEquals(u2, u3);
		assertEquals(u3.getEMail(), "testemail2");
		
		dao.removeUser(u3);
		assertTrue(u3.getId() < 0);
		assertFalse(u.isAdmin() || u2.isAdmin() || u3.isAdmin());
		
		User u4 = dao.getUserForName(u.getName());
		assertNull(u4);
	}
	
	@Test
	public void testStatements() throws DAOException {
		Statement s = dao.getStatementForId(1);
		assertTrue(s != null);
		
		Statement s2 = dao.getStatementsForUser(dao.getUserForId(1)).get(0);
		assertTrue(s2 != null);
		
		assertEquals(s.getName(), s2.getName());
		
		User u = dao.getUserForId(1);
		Statement s3 = new Statement("another test", "another testdescription", u);
		dao.saveStatement(s3);
		assertTrue(s3.getId() > 0);
		
		Statement s4 = dao.getStatementForId(s3.getId());
		assertNull(s4.getResolved());
		Date now = new Date();
		s4.setResolved(now);
		s4.setPositiveOutcome(true);
		dao.saveStatement(s4);
		
		Statement s5 = dao.getStatementForId(s3.getId());
		assertTrue(Math.abs(now.getTime() - s5.getResolved().getTime()) < 2000);
		assertTrue(s5.isPositiveOutcome());
	}
}
