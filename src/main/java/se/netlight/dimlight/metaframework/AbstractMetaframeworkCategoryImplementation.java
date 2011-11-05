package se.netlight.dimlight.metaframework;

public abstract class AbstractMetaframeworkCategoryImplementation extends AbstractDisplayableEntity {
	private boolean defaultImplementation;
	
	
	public void setDefaultImplementation(boolean defaultImplementation) {
		this.defaultImplementation = defaultImplementation;
	}

	public boolean isDefaultImplementation() {
		return defaultImplementation;
	}
	
}
