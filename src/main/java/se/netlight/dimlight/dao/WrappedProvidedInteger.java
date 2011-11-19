package se.netlight.dimlight.dao;


public class WrappedProvidedInteger extends ProvidedInteger {
	private int value;

	public WrappedProvidedInteger(int value) {
		this.value = value;
	}

	@Override
	public int asInteger() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@Override
	public ProvidedInteger add(int amount) {
		return new WrappedProvidedInteger(value + amount);
	}	
}