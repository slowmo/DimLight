package se.netlight.dimlight.metaframework;

public class MetaframeworkCategoryImplementation {
	private Object implementation;
	private String name;
	private boolean defaultImplementation;
	
	public Object getImplementation() {
		return implementation;
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
