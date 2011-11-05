package se.netlight.dimlight.dao;

import java.util.List;

import se.netlight.dimlight.objects.Bet;
import se.netlight.dimlight.objects.Message;
import se.netlight.dimlight.objects.Statement;
import se.netlight.dimlight.objects.User;

public interface IDimlightDAO {
	public User getUserForId(int id) throws DAOException;
	public User getUserForName(String name) throws DAOException;
	public void saveUser(User user) throws DAOException;
	public void removeUser(User user) throws DAOException;
	
	public Statement getStatementForId(int id) throws DAOException;
	public void saveStatement(Statement statement) throws DAOException;
	public void deleteStatement(Statement statement) throws DAOException;
	public List<Statement> getStatementsForUser(User user) throws DAOException;
	public List<Statement> getOpenStatements();
	public List<Statement> getAllStatements();
	
	public Bet getBetForId(int id) throws DAOException;
	public void saveBet(Bet bet) throws DAOException;
	public void removeBet(Bet bet) throws DAOException;
	public List<Bet> getBetsForStatement(Statement s) throws DAOException;
	public Bet getBetsForStatementAndUser(Statement s, User user) throws DAOException;
	
	public Message getMessageForId(int id) throws DAOException;
	public void saveMessage(Message message) throws DAOException;
	public void removeMessage(Message message) throws DAOException;
	public List<Message> getOpenMessagesForUser(User user) throws DAOException;
	public List<Message> getAllMessagesForUser(User user) throws DAOException;
}
