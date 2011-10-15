package se.netlight.dimlight.metaframework;

public class MetaframeworkCategoryImplementation {
	private Object implementation;
	private String name;
	private boolean defaultImplementation;
	
	@SuppressWarnings("unchecked")
	public <T> T getImplementation() {
		return (T) implementation;
	}

	public void setImplementation(Object implementation) {
		this.implementation = implementation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDefaultImplementation() {
		return defaultImplementation;
	}

	public void setDefaultImplementation(boolean defaultImplementation) {
		this.defaultImplementation = defaultImplementation;
	}
}
