package se.netlight.dimlight.objects;

import java.util.Date;

public class Statement {
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getResolved() {
		return resolved;
	}

	public void setResolved(Date resolved) {
		this.resolved = resolved;
	}

	public boolean isPositiveOutcome() {
		return positiveOutcome;
	}

	public void setPositiveOutcome(boolean positiveOutcome) {
		this.positiveOutcome = positiveOutcome;
	}

	private int id;
	private String name;
	private String description;
	private User creator;
	private Date created;
	private Date resolved;
	private boolean positiveOutcome;
	
	public Statement() {
	}
	
	public Statement(String name, String description, Date created,
			Date resolved, boolean positiveOutcome) {
		this.name = name;
		this.description = description;
		this.created = created;
		this.resolved = resolved;
		this.positiveOutcome = positiveOutcome;
	}

	public Statement(String name, String description, User u) {
		this.name = name;
		this.description = description;
		this.creator = u;
	}
}
