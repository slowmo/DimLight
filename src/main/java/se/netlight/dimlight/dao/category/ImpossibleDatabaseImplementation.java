package se.netlight.dimlight.dao.category;

import org.hsqldb.HsqlException;

import se.netlight.dimlight.dao.ProvidedInteger;
import se.netlight.dimlight.dao.WrappedProvidedInteger;

public class ImpossibleDatabaseImplementation extends AbstractDatabaseCategoryImplementation {
	@Override
	public ProvidedInteger buildProvidedInteger(String amount) {
		return new WrappedProvidedInteger(amount);
	}

	@Override
	public boolean shouldSuppressException(Exception ex) {
		Throwable e = ex;
		while (e != null) {
			if (e instanceof HsqlException)
				return true;
			
			e = e.getCause();
		}
		return false;
	}

	@Override
	public String sanitizeString(String str) {
		str = str.replace(";", ":");
		str = str.replaceAll("--", "");
		str = str.replaceAll("'", "''");
		return str;
	}
}
