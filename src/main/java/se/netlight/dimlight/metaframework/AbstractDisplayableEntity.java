package se.netlight.dimlight.metaframework;

public abstract class AbstractDisplayableEntity {
	private String name, display, description;

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getDisplay() {
		return display;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
