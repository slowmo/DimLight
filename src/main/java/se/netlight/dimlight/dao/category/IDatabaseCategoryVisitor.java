package se.netlight.dimlight.dao.category;

public interface IDatabaseCategoryVisitor<T, E extends Exception> {

	public T visitStupidImplementation() throws E;

}
