package se.netlight.dimlight.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.asm.Type;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.object.SqlUpdate;

import se.netlight.dimlight.objects.Bet;
import se.netlight.dimlight.objects.Message;
import se.netlight.dimlight.objects.Statement;
import se.netlight.dimlight.objects.User;

public class StupidDimlightDAO extends JDBCDimlightDAO {
	private RowMapper<User> userRowMapper = new RowMapper<User>(){
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User u = new User();
			u.setId(rs.getInt(1));
			u.setName(rs.getString(2));
			u.setEMail(rs.getString(3));
			u.setAdmin(rs.getBoolean(4));
			u.setPassword(rs.getString(5));
			u.setBalance(rs.getDouble(6));
			u.setSecret(rs.getString(7));
			return u;
		}};
		
	private RowMapper<Statement> statementRowMapper = new RowMapper<Statement>(){
		public Statement mapRow(ResultSet rs, int rowNum) throws SQLException {
			Statement s = new Statement(rs.getString(2), rs.getString(3), rs.getTimestamp(5), rs.getTimestamp(6), rs.getBoolean(7));
			s.setId(rs.getInt(1));
			try {
				s.setCreator(getUserForId(rs.getInt(4)));
			} catch (DAOException e) {
				throw new SQLException("Subquery failed", e);
			}
			return s;
		}
	};
	
	class StatementUpdate extends SqlUpdate {
		public StatementUpdate(DataSource dataSource) {
			super(dataSource, 
					"UPDATE Statement SET name=?,description=?,created=?,resolved=?,outcome=? WHERE id=?", 
					new int[]{Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.DATE, Types.BOOLEAN, Type.INT});
			compile();
		}
		
		public int apply(Statement s) {
			Object[] params = new Object[]{s.getName(), s.getDescription(), s.getCreated(), s.getResolved(), new Boolean(s.isPositiveOutcome()), new Integer(s.getId())};
			return update(params);
		}
	}
		
	private RowMapper<Bet> betRowMapper = new RowMapper<Bet>() {
		public Bet mapRow(ResultSet rs, int rowNum) throws SQLException {
			Bet b = new Bet(rs.getTimestamp(2), rs.getBoolean(5), rs.getDouble(6));
			b.setId(rs.getInt(1));
			try {
				b.setUser(getUserForId(rs.getInt(3)));
				b.setStatement(getStatementForId(rs.getInt(4)));
			} catch (DAOException e) {
				throw new SQLException("Subquery failed", e);
			}
			return b;
		}
	};
	
	private RowMapper<Message> messageRowMapper = new RowMapper<Message>() {
		public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
			Message m = new Message(rs.getString(3), rs.getTimestamp(4), rs.getTimestamp(5));
			m.setId(rs.getInt(1));
			try {
				m.setUser(getUserForId(rs.getInt(2)));
			} catch (DAOException e) {
				throw new SQLException("Subquery failed", e);	
			}
			return m;
		}
	};

	private StatementUpdate statementUpdater;
		
	public User getUserForId(int id) throws DAOException {
		User ret = template.queryForObject("SELECT * FROM User WHERE id = " + id, userRowMapper);
		return ret;
	}

	public User getUserForName(String name) throws DAOException {
		return template.queryForObject("SELECT * FROM User WHERE name='" + name + "'", userRowMapper);
	}
	
	public List<User> getAllUsers() throws DAOException {
		return template.query("SELECT * FROM User", userRowMapper);
	}

	public void saveUser(User user) throws DAOException {
		if (user.getId() <= 0) {
			template.execute("INSERT INTO User (name, email, admin, password, balance, secret) VALUES ('"
				+ user.getName() + "','"
				+ user.getEMail() + "',0,'"
				+ user.getPassword() + "',"
				+ user.getBalance() + ",'"
				+ user.getSecret() + "')"
			);
			User u2 = getUserForName(user.getName());
			user.setId(u2.getId());
		} else {
			template.execute("UPDATE User SET '"
					+ "name='"+ user.getName() + "',"
					+ "email='" +user.getEMail() + "',"
					+ "password='" + user.getPassword() + "',"
					+ "balance=" + user.getBalance() + ",'"
					+ "secret='" + user.getSecret() + "' "
					+ "WHERE id=" + user.getId()
				);
		}
	}

	public void removeUser(User user) throws DAOException {
		template.execute("DELETE FROM User WHERE id = " + user.getId());
		user.setId(-1);
	}

	public Statement getStatementForId(int id) throws DAOException {
		return template.queryForObject("SELECT * FROM Statement WHERE id=" + id, statementRowMapper);
	}

	public void saveStatement(Statement statement) throws DAOException {
		if (statement.getId() <= 0) {
			template.execute("INSERT INTO Statement (name, description, creator_id, created, resolved, outcome) VALUES ("
				+ "'" + statement.getName() + "',"
				+ "'" + statement.getDescription() + "',"
				+ statement.getCreator().getId() + ","
				+ "CURRENT_TIME,NULL, FALSE)"
			);
			Integer id = template.queryForObject("SELECT MAX(id) FROM Statement", Integer.class);
			statement.setId(id);
		} else {
			statementUpdater.apply(statement);
		}
	}

	public void deleteStatement(Statement statement) throws DAOException {
		template.execute("DELETE FROM Statement WHERE id = " + statement.getId());
		statement.setId(-1);
	}

	public List<Statement> getStatementsForUser(User user) throws DAOException {
		return template.query("SELECT * FROM Statement WHERE creator_id=" + user.getId(), statementRowMapper);
	}

	public List<Statement> getOpenStatements() {
		return template.query("SELECT * FROM Statement WHERE resolved is null", statementRowMapper);
	}

	public List<Statement> getAllStatements() {
		return template.query("SELECT * FROM Statement", statementRowMapper);
	}

	public Bet getBetForId(int id) throws DAOException {
		return template.queryForObject("SELECT * FROM Bet WHERE id=" + id, betRowMapper);
	}

	public void saveBet(Bet bet) throws DAOException {
		if (bet.getId() >= 0) {
			throw new DAOException("Cannot modify a bet");
		}
		template.execute("INSERT Bet (CREATED, USER_ID, STATEMENT_ID, POSITIVE, AMOUNT) VALUES ("
			+ "NOW(), "
			+ bet.getUser().getId() + ","
			+ bet.getStatement().getId() + ","
			+ (bet.isPositive()?"TRUE":"FALSE") + ","
			+ bet.getAmount() + ")"
		);
		int id = template.queryForObject("SELECT MAX(id) FROM Bet", Integer.class);
		bet.setId(id);
	}

	public void removeBet(Bet bet) throws DAOException {
		template.execute("DELETE FROM Bet WHERE id = " + bet.getId());
		bet.setId(-1);
	}

	public List<Bet> getBetsForStatement(Statement s) throws DAOException {
		return template.query("SELECT * FROM Bet WHERE statement_id=" + s.getId(), betRowMapper);
	}

	public Bet getBetForStatementAndUser(Statement s, User user) throws DAOException {
		return template.queryForObject("SELECT * FROM Bet WHERE statement_id=" + s.getId() + " and user_id=" + user.getId(), betRowMapper);
	}

	public Message getMessageForId(int id) throws DAOException {
		return template.queryForObject("SELECT * FROM Message WHERE id=" + id, messageRowMapper);
	}

	public void saveMessage(Message message) throws DAOException {
		if (message.getId() > 0) {
			throw new DAOException("Cannot save a message");
		}
		template.execute("INSERT INTO Message (user_id, content, created) VALUES ("
				+ message.getUser().getId() + ","
				+ "'" + message.getContent() + "',"
				+ "NOW())"
			);
		int id = template.queryForObject("SELECT MAX(id) FROM Message", Integer.class);
		message.setId(id);		
	}
	
	public void markMessageRead(Message m) {
		template.execute("UPDATE Message SET read=NOW() WHERE id=" + m.getId());
	}

	public void removeMessage(Message message) throws DAOException {
		template.execute("DELETE FROM Message WHERE id = " + message.getId());
		message.setId(-1);
	}

	public List<Message> getOpenMessagesForUser(User user) throws DAOException {
		return template.query("SELECT * FROM Message WHERE read is null and user_id=" + user.getId(), messageRowMapper);
	}

	public List<Message> getAllMessagesForUser(User user) throws DAOException {
		return template.query("SELECT * FROM Message WHERE user_id=" + user.getId(), messageRowMapper);
	}

	@Override
	protected void prepare(DataSource source) {
		statementUpdater = new StatementUpdate(source);
	}
}
