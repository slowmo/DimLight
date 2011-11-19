package se.netlight.dimlight.dao;

/** Since we want to allow for really stupid things, we use this abstraction for how integer values
 * are given to the DAO. 
 * 
 * @author Mo
 *
 */
public abstract class ProvidedInteger {
	public abstract int asInteger();
	public abstract String toString();

	public static ProvidedInteger wrap(final int value) {
		return new WrappedProvidedInteger(value);
	}

	public abstract ProvidedInteger add(int amount);
}

