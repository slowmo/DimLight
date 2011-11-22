package se.netlight.dimlight.metaframework;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetaframeworkCategory<T extends AbstractMetaframeworkCategoryImplementation>  extends AbstractDisplayableEntity {
	private List<AbstractMetaframeworkCategoryImplementation> implementations;
	private Map<String, AbstractMetaframeworkCategoryImplementation> implementationsMap;
	private AbstractMetaframeworkCategoryImplementation defaultImplementation;
		
	public void setImplementations(List<AbstractMetaframeworkCategoryImplementation> implementations) {
		this.implementations = implementations;
		implementationsMap = new HashMap<String, AbstractMetaframeworkCategoryImplementation>(implementations.size());
		for (AbstractMetaframeworkCategoryImplementation mci : implementations) {
			implementationsMap.put(mci.getName(), mci);
			if (defaultImplementation == null)
				defaultImplementation = mci;
		}
		if (defaultImplementation == null)
			throw new IllegalArgumentException("No default implementation provided");
	}

	@SuppressWarnings("unchecked")
	public T getImplementation(String choice) {
		if (choice == null)
			return (T) defaultImplementation;
		AbstractMetaframeworkCategoryImplementation mci = implementationsMap.get(choice);
		if (mci == null)
			mci = defaultImplementation;
		
		return (T) mci;
	}

	@SuppressWarnings("unchecked")
	public T getDefaultImplementation() {
		return (T) defaultImplementation;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getImplementations() {
		return (List<T>) implementations;
	}
}
