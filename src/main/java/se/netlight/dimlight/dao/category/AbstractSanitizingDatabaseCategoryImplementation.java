package se.netlight.dimlight.dao.category;


public abstract class AbstractSanitizingDatabaseCategoryImplementation extends AbstractDatabaseCategoryImplementation {
	@Override
	public String sanitizeString(String str) {
		str = str.replace(";", ":");
		str = str.replaceAll("--", "");
		return str;
	}
}
