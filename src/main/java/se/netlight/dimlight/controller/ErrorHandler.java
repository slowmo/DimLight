package se.netlight.dimlight.controller;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import se.netlight.dimlight.dao.category.AbstractDatabaseCategoryImplementation;
import se.netlight.dimlight.metaframework.MetaframeworkManager;
import se.netlight.dimlight.metaframework.session.DimlightMetaframeworkSession;
import se.netlight.dimlight.metaframework.session.DimlightMetaframeworkSessionManager;

public class ErrorHandler {
	private static Logger logger = Logger.getLogger(AbstractDimlightController.class); 

	public static ModelAndView handleException(Exception ex) throws Exception {
		logger.error("Exception encountered", ex);
		DimlightMetaframeworkSession session = DimlightMetaframeworkSessionManager.getCurrentSession();		
		AbstractDatabaseCategoryImplementation daoImpl = MetaframeworkManager.getInstance().getSelectedImplementation("database", session.getContext("database"));
		if (daoImpl.shouldSuppressException(ex))
			return new ModelAndView("error", "message", "An error has occured, sorry.");

		throw ex;
	}

}
