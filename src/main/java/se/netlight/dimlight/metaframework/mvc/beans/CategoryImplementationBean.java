package se.netlight.dimlight.metaframework.mvc.beans;

public class CategoryImplementationBean {
	private String name;
	private String description;
	private String display; 
	private boolean selected;

	public CategoryImplementationBean(String name, String display, String description, boolean selected) {
		this.setName(name);
		this.setDescription(description);
		this.setDisplay(display);
		this.selected = selected;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

}
