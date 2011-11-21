package se.netlight.dimlight.metaframework.mvc.beans;

import java.util.ArrayList;
import java.util.List;

import se.netlight.dimlight.metaframework.AbstractMetaframeworkCategoryImplementation;
import se.netlight.dimlight.metaframework.MetaframeworkCategory;
import se.netlight.dimlight.metaframework.MetaframeworkManager;
import se.netlight.dimlight.metaframework.session.DimlightMetaframeworkSessionManager;


public class MetaFrameworkBean {
	public MetaFrameworkBean() {
		
	}	
	
	public List<CategoryBean> getCategories() {
		List<MetaframeworkCategory<?>> categories = MetaframeworkManager.getInstance().getCategories();
		List<CategoryBean> res = new ArrayList<CategoryBean>(categories.size());
		for (MetaframeworkCategory<?> c : categories) {
			res.add(buildCategoryBean(c));
		}
		return res;
	}

	private CategoryBean buildCategoryBean(MetaframeworkCategory<?> c) {
		CategoryBean b = new CategoryBean();
		b.setName(c.getName());
		b.setDescription(c.getDescription());
		b.setDisplay(c.getDisplay());
		
		List<? extends AbstractMetaframeworkCategoryImplementation> implementations = c.getImplementations();
		List<CategoryImplementationBean> implbeans = new ArrayList<CategoryImplementationBean>(implementations.size());
		String choice = DimlightMetaframeworkSessionManager.getCurrentSession().getContext(c.getName());
		for (AbstractMetaframeworkCategoryImplementation impl : implementations) {
			boolean current = choice != null ? choice.equals(impl.getName()) : impl.isDefaultImplementation();
			CategoryImplementationBean implbean = new CategoryImplementationBean(impl.getName(), impl.getDisplay(), impl.getDescription(), current);
			implbeans.add(implbean);
		}
		b.setImplementations(implbeans);
		return b;
	}
}
