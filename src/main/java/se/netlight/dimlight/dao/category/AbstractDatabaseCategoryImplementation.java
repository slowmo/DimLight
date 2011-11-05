package se.netlight.dimlight.dao.category;

import se.netlight.dimlight.dao.IDimlightDAO;
import se.netlight.dimlight.metaframework.AbstractMetaframeworkCategoryImplementation;

public abstract class AbstractDatabaseCategoryImplementation extends AbstractMetaframeworkCategoryImplementation {
	private IDimlightDAO dao;

	public void setDao(IDimlightDAO dao) {
		this.dao = dao;
	}

	public IDimlightDAO getDao() {
		return dao;
	}
	
	public abstract <T, E extends Exception> T visit(IDatabaseCategoryVisitor<T, E> v) throws E;
}
