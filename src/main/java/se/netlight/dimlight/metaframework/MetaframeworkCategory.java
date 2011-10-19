package se.netlight.dimlight.metaframework;

import java.util.List;
import java.util.Map;

public class MetaframeworkCategory<T> {
	private String name;
	private List<MetaframeworkCategoryImplementation> implementations;
	private Map<String, MetaframeworkCategoryImplementation> implementationsMap;
	private MetaframeworkCategoryImplementation defaultImplementation;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

		
	public void setImplementations(List<MetaframeworkCategoryImplementation> implementations) {
		this.implementations = implementations;
		for (MetaframeworkCategoryImplementation mci : implementations) {
			implementationsMap.put(mci.getName(), mci);
			if (mci.isDefaultImplementation())
				defaultImplementation = mci;
		}
		if (defaultImplementation == null)
			throw new IllegalArgumentException("No default implementation provided");
	}

	@SuppressWarnings("unchecked")
	public T getImplementation(String choice) {
		MetaframeworkCategoryImplementation mci = implementationsMap.get(choice);
		if (mci == null)
			mci = defaultImplementation;
		
		return (T) mci.getImplementation();
	}

	public MetaframeworkCategoryImplementation getDefaultImplementation() {
		return defaultImplementation;
	}
	
	public List<MetaframeworkCategoryImplementation> getImplementations() {
		return implementations;
	}
}
