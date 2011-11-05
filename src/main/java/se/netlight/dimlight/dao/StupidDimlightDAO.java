package se.netlight.dimlight.dao;

import java.util.List;

import se.netlight.dimlight.objects.Bet;
import se.netlight.dimlight.objects.Message;
import se.netlight.dimlight.objects.Statement;
import se.netlight.dimlight.objects.User;

public class StupidDimlightDAO extends JDBCDimlightDAO {
	public User getUserForId(int id) throws DAOException {
		User ret = template.queryForObject("SELECT * FROM User WHERE id = " + id, User.class);
		return ret;
	}

	public User getUserForName(String name) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveUser(User user) throws DAOException {
		// TODO Auto-generated method stub

	}

	public void removeUser(User user) throws DAOException {
		// TODO Auto-generated method stub

	}

	public Statement getStatementForId(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveStatement(Statement statement) throws DAOException {
		// TODO Auto-generated method stub

	}

	public void deleteStatement(Statement statement) throws DAOException {
		// TODO Auto-generated method stub

	}

	public List<Statement> getStatementsForUser(User user) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Statement> getOpenStatements() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Statement> getAllStatements() {
		// TODO Auto-generated method stub
		return null;
	}

	public Bet getBetForId(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveBet(Bet bet) throws DAOException {
		// TODO Auto-generated method stub

	}

	public void removeBet(Bet bet) throws DAOException {
		// TODO Auto-generated method stub

	}

	public List<Bet> getBetsForStatement(Statement s) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	public Bet getBetsForStatementAndUser(Statement s, User user)
			throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	public Message getMessageForId(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveMessage(Message message) throws DAOException {
		// TODO Auto-generated method stub

	}

	public void removeMessage(Message message) throws DAOException {
		// TODO Auto-generated method stub

	}

	public List<Message> getOpenMessagesForUser(User user) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Message> getAllMessagesForUser(User user) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
