package se.netlight.dimlight.dao;

import java.util.List;

import se.netlight.dimlight.dao.category.AbstractDatabaseCategoryImplementation;
import se.netlight.dimlight.metaframework.MetaframeworkManager;
import se.netlight.dimlight.metaframework.session.DimlightMetaframeworkSessionManager;
import se.netlight.dimlight.objects.Bet;
import se.netlight.dimlight.objects.Message;
import se.netlight.dimlight.objects.Statement;
import se.netlight.dimlight.objects.User;

public class SanitizingDAOAdapter implements IDimlightDAO {
	private IDimlightDAO dao;
	
	public void setDao(IDimlightDAO dao) {
		this.dao = dao;
	}

	private String sanitize(String str) {
		AbstractDatabaseCategoryImplementation daoImpl = MetaframeworkManager.getInstance().getSelectedImplementation("database", DimlightMetaframeworkSessionManager.getCurrentSession().getContext("database"));
		return daoImpl.sanitizeString(str);
	}

	
	public User getUserForId(ProvidedInteger id) throws DAOException {
		return dao.getUserForId(id);
	}

	public User getUserForName(String name) throws DAOException {		
		return dao.getUserForName(sanitize(name));
	}


	public void saveUser(User user) throws DAOException {
		user.setEMail(sanitize(user.getEMail()));
		user.setSecret(sanitize(user.getEMail()));
		dao.saveUser(user);
	}

	public void changeUserBalance(User user, ProvidedInteger balance)
			throws DAOException {
		dao.changeUserBalance(user, balance);
	}

	public void changeUserSecret(User user, String secret) throws DAOException {
		dao.changeUserSecret(user, sanitize(secret));
	}

	public void removeUser(User user) throws DAOException {
		dao.removeUser(user);
	}

	public Statement getStatementForId(ProvidedInteger id) throws DAOException {
		return dao.getStatementForId(id);
	}

	public void saveStatement(Statement statement) throws DAOException {
		statement.setDescription(sanitize(statement.getDescription()));
		statement.setName(sanitize(statement.getName()));		
		dao.saveStatement(statement);
	}

	public void deleteStatement(Statement statement) throws DAOException {
		dao.deleteStatement(statement);
	}

	public List<Statement> getStatementsForUser(User user) throws DAOException {
		return dao.getStatementsForUser(user);
	}

	public List<Statement> getOpenStatementsForUser(User user)
			throws DAOException {
		return dao.getOpenStatementsForUser(user);
	}

	public List<Statement> getOpenStatements() {
		return dao.getOpenStatements();
	}

	public List<Statement> getAllStatements() {
		return dao.getAllStatements();
	}

	public List<Statement> getLastStatements(ProvidedInteger count) {
		return dao.getLastStatements(count);
	}

	public Bet getBetForId(ProvidedInteger id) throws DAOException {
		return dao.getBetForId(id);
	}

	public void saveBet(Bet bet) throws DAOException {
		dao.saveBet(bet);
	}

	public void removeBet(Bet bet) throws DAOException {
		dao.removeBet(bet);
	}

	public List<Bet> getBetsForStatement(ProvidedInteger statementId)
			throws DAOException {
		return dao.getBetsForStatement(statementId);
	}

	public Bet getBetForStatementAndUser(Statement s, User user)
			throws DAOException {
		return dao.getBetForStatementAndUser(s, user);
	}

	public List<Bet> getOpenBetsForUser(User u) throws DAOException {
		return dao.getOpenBetsForUser(u);
	}

	public List<Bet> getBetsForStatementAndUser(ProvidedInteger statementId,
			User user) {
		return dao.getBetsForStatementAndUser(statementId, user);
	}

	public void storeBet(Statement s, User u, boolean betOnSuccess,
			ProvidedInteger amount) {
		dao.storeBet(s, u, betOnSuccess, amount);
	}

	public Message getMessageForId(ProvidedInteger id) throws DAOException {
		return dao.getMessageForId(id);
	}

	public void saveMessage(Message message) throws DAOException {
		message.setContent(sanitize(message.getContent()));
	}

	public void removeMessage(Message message) throws DAOException {
		dao.removeMessage(message);
	}

	public void markMessageRead(ProvidedInteger id) throws DAOException {
		dao.markMessageRead(id);
	}

	public List<Message> getOpenMessagesForUser(User user) throws DAOException {		
		return dao.getOpenMessagesForUser(user);
	}

	public List<Message> getAllMessagesForUser(User user) throws DAOException {
		return dao.getAllMessagesForUser(user);
	}
}
