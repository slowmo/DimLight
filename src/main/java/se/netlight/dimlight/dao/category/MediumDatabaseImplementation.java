package se.netlight.dimlight.dao.category;

import se.netlight.dimlight.dao.ProvidedInteger;
import se.netlight.dimlight.dao.WrappedProvidedInteger;

public class MediumDatabaseImplementation extends AbstractDatabaseCategoryImplementation {
	@Override
	public ProvidedInteger buildProvidedInteger(String amount) {
		return new WrappedProvidedInteger(amount);
	}

	@Override
	public boolean shouldSuppressException(Exception ex) {
		return false;
	}

}
