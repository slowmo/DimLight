package se.netlight.dimlight.metaframework.mvc.beans;

import java.util.List;

public class CategoryBean {
	private String name, description, display;
	private List<CategoryImplementationBean> implementations;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setImplementations(List<CategoryImplementationBean> implbeans) {
		this.implementations = implbeans;		
	}
	
	public List<CategoryImplementationBean> getImplementations() {
		return implementations;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}
}
