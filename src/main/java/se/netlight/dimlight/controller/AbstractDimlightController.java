package se.netlight.dimlight.controller;

import se.netlight.dimlight.dao.IDimlightDAO;
import se.netlight.dimlight.dao.category.AbstractDatabaseCategoryImplementation;
import se.netlight.dimlight.metaframework.MetaframeworkManager;
import se.netlight.dimlight.metaframework.session.DimlightSession;
import se.netlight.dimlight.metaframework.session.DimlightSessionManager;

public abstract class AbstractDimlightController {
	protected IDimlightDAO getDao() {
		DimlightSession session = DimlightSessionManager.getCurrentSession();		
		AbstractDatabaseCategoryImplementation daoImpl = MetaframeworkManager.getInstance().getSelectedImplementation("database", session.getContext("database"));
		return daoImpl.getDao();
	}
}
