package se.netlight.dimlight.objects;

import java.util.Date;

public class Message {
	private int id;
	private String content;
	private Date created;
	private Date read;
	private User user;

	public Message() {
	}
	
	public Message(String content, User user) {
		this.content = content;
		this.created = new Date();
		this.user = user;
	}

	public Message(String content, Date created, Date read) {
		this.content = content;
		this.created = created;
		this.read = read;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getRead() {
		return read;
	}

	public void setRead(Date read) {
		this.read = read;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}
}
