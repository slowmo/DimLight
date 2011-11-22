package se.netlight.dimlight.dao;

import java.util.List;

import se.netlight.dimlight.objects.Bet;
import se.netlight.dimlight.objects.Message;
import se.netlight.dimlight.objects.Statement;
import se.netlight.dimlight.objects.User;

public interface IDimlightDAO {
	public User getUserForId(ProvidedInteger id) throws DAOException;
	public User getUserForName(String name) throws DAOException;
	public void saveUser(User user) throws DAOException;
	public void changeUserBalance(User user, ProvidedInteger balance) throws DAOException;
	public void changeUserSecret(User user, String secret) throws DAOException;
	public void removeUser(User user) throws DAOException;
	
	public Statement getStatementForId(ProvidedInteger id) throws DAOException;
	public void saveStatement(Statement statement) throws DAOException;
	public void deleteStatement(Statement statement) throws DAOException;
	public List<Statement> getStatementsForUser(User user) throws DAOException;
	public List<Statement> getOpenStatementsForUser(User user) throws DAOException;
	public List<Statement> getOpenStatements();
	public List<Statement> getAllStatements();
	public List<Statement> getLastStatements(ProvidedInteger count);
		
	public Bet getBetForId(ProvidedInteger id) throws DAOException;
	public void saveBet(Bet bet) throws DAOException;
	public void removeBet(Bet bet) throws DAOException;
	public List<Bet> getBetsForStatement(ProvidedInteger statementId) throws DAOException;
	public Bet getBetForStatementAndUser(Statement s, User user) throws DAOException;
	public List<Bet> getOpenBetsForUser(User u) throws DAOException;
	public List<Bet> getBetsForStatementAndUser(ProvidedInteger statementId, User user);
	public void storeBet(Statement s, User u, boolean betOnSuccess,	ProvidedInteger amount);
	
	public Message getMessageForId(ProvidedInteger id) throws DAOException;
	public void saveMessage(Message message) throws DAOException;
	public void removeMessage(Message message) throws DAOException;
	public void markMessageRead(ProvidedInteger id) throws DAOException;
	public List<Message> getOpenMessagesForUser(User user) throws DAOException;
	public List<Message> getAllMessagesForUser(User user) throws DAOException;
}
