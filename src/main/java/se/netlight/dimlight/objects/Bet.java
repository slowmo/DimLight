package se.netlight.dimlight.objects;

import java.util.Date;

public class Bet {
	public Bet(Date created, boolean positive, int amount) {
		this.created = created;
		this.positive = positive;
		this.amount = amount;
	}

	public Bet() {
	}
	
	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public boolean isPositive() {
		return positive;
	}

	public void setPositive(boolean positive) {
		this.positive = positive;
	}

	public int getAmount() {
		return amount;
	}
	
	public String getPositiveString() {
		return positive?"sucess":"failure";
	}

	public Bet(Statement statement, User user, boolean positive, int amount) {
		this.statement = statement;
		this.user = user;
		this.positive = positive;
		this.amount = amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	private Statement statement;
	private User user;
	private int id;
	private Date created;
	private boolean positive;
	private int amount;
}
