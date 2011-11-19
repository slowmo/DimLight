package se.netlight.dimlight.model;

import java.util.List;

import se.netlight.dimlight.objects.Statement;
import se.netlight.dimlight.objects.User;


public class IndexViewBean {
	private List<Statement> statements;
	private String error;
	private User user;

	public IndexViewBean(List<Statement> lastStatements, User user) {
		this.statements = lastStatements;
		this.user = user;
	}

	public List<Statement> getStatements() {
		return statements;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public boolean isErrorSet() {
		return error != null;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public boolean isLoggedin() {
		return user != null;
	}
}

