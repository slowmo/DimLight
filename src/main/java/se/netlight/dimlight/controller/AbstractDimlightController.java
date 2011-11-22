package se.netlight.dimlight.controller;

import javax.servlet.http.HttpSession;

import se.netlight.dimlight.dao.DAOException;
import se.netlight.dimlight.dao.IDimlightDAO;
import se.netlight.dimlight.dao.ProvidedInteger;
import se.netlight.dimlight.dao.category.AbstractDatabaseCategoryImplementation;
import se.netlight.dimlight.metaframework.MetaframeworkManager;
import se.netlight.dimlight.metaframework.session.DimlightMetaframeworkSession;
import se.netlight.dimlight.metaframework.session.DimlightMetaframeworkSessionManager;
import se.netlight.dimlight.objects.User;

public abstract class AbstractDimlightController {
	protected IDimlightDAO getDao() {
		DimlightMetaframeworkSession session = DimlightMetaframeworkSessionManager.getCurrentSession();		
		AbstractDatabaseCategoryImplementation daoImpl = MetaframeworkManager.getInstance().getSelectedImplementation("database", session.getContext("database"));
		return daoImpl.getDao();
	}
	
	protected ProvidedInteger wrapNumericParameter(String id) {
		AbstractDatabaseCategoryImplementation daoImpl = MetaframeworkManager.getInstance().getSelectedImplementation("database", DimlightMetaframeworkSessionManager.getCurrentSession().getContext("database"));
		return daoImpl.buildProvidedInteger(id);
	}
	
	protected User loadUser(HttpSession session, boolean userRequired) {
		User u = (User) session.getAttribute("user");
		if (u == null)
			if (userRequired)
				throw new RuntimeException("Need to be logged in for this page");
			else
				return null;

		// reload, to see any freshly hacked data :-)
		try {
			u = getDao().getUserForId(ProvidedInteger.wrap(u.getId()));
		} catch (DAOException e) {
			throw new RuntimeException("Failed to load user", e);
		}
		
		return u;
	}

}
