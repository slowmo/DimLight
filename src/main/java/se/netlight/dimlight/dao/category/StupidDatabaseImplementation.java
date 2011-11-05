package se.netlight.dimlight.dao.category;

public class StupidDatabaseImplementation extends AbstractDatabaseCategoryImplementation {
	@Override
	public <T, E extends Exception> T visit(IDatabaseCategoryVisitor<T, E> v) throws E {
		return v.visitStupidImplementation();
	}
}
