package se.netlight.dimlight.dao.category;

import se.netlight.dimlight.dao.ProvidedInteger;
import se.netlight.dimlight.dao.VerbatimProvidedInteger;

public class StupidDatabaseImplementation extends AbstractDatabaseCategoryImplementation {
	@Override
	public ProvidedInteger buildProvidedInteger(String amount) {
		return new VerbatimProvidedInteger(amount);
	}

	@Override
	public boolean shouldSuppressException(Exception ex) {
		return false;
	}

	@Override
	public String sanitizeString(String str) {
		return str;
	}
}
