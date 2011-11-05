package se.netlight.dimlight.metaframework;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This manager is the central hub for session-dependend managers. It gets the various categories 
 * and the choices by Spring injection, and provides lookup facilities for the web application.
 * 
 *  The manager itself is implemented as a Singleton. 
 * 
 * @author Moritz Hammer
 *
 */
public class MetaframeworkManager {
	private static MetaframeworkManager instance;
	private ICategoryConfigurationStorage categoryConfigurationStorage;

	public static MetaframeworkManager getInstance() {
		if (instance == null)
			throw new IllegalStateException("Trying to get Singleton instance before it is instantiated");
		return instance;
	}

	private Map<String, MetaframeworkCategory<?>> categoriesMap;
	private List<MetaframeworkCategory<?>> categories;
	
	public MetaframeworkManager() {
		if (instance != null)
			throw new IllegalStateException("Trying to instantiate the Singleton twice");
		instance = this;
	}
	
	public void setCategories(List<MetaframeworkCategory<?>> categories) {
		this.categoriesMap = new HashMap<String, MetaframeworkCategory<?>>(categories.size());
		this.categories = categories;
		for (MetaframeworkCategory<?> mc : categories)
			this.categoriesMap.put(mc.getName(), mc);
	}
	
	public List<MetaframeworkCategory<?>> getCategories() {
		return categories;
	}
	
	public void setCategoryConfigurationStorage(
			ICategoryConfigurationStorage categoryConfigurationStorage) {
		this.categoryConfigurationStorage = categoryConfigurationStorage;
	}

	@SuppressWarnings("unchecked")
	public <T extends AbstractMetaframeworkCategoryImplementation> T getSelectedImplementation(String category, String choice) {
		MetaframeworkCategory<T> c = (MetaframeworkCategory<T>) categoriesMap.get(category);
		if (c == null)
			throw new IllegalArgumentException("Unknown category " + category);
		
		return c.getImplementation(choice);
	}
	
	public <T extends AbstractMetaframeworkCategoryImplementation> T getConfiguredImplementation(String category) {
		String choice = categoryConfigurationStorage.getCategoryChoice(category);
		return getSelectedImplementation(category, choice);
	}
}
