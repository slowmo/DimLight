package se.netlight.dimlight.dao.category;

import se.netlight.dimlight.dao.ProvidedInteger;
import se.netlight.dimlight.dao.VerbatimProvidedInteger;

public class StupidDatabaseImplementation extends AbstractDatabaseCategoryImplementation {
	@Override
	public <T, E extends Exception> T visit(IDatabaseCategoryVisitor<T, E> v) throws E {
		return v.visitStupidImplementation();
	}

	@Override
	public ProvidedInteger buildProvidedInteger(String amount) {
		return new VerbatimProvidedInteger(amount);
	}
}
