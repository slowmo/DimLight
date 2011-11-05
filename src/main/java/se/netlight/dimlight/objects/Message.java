package se.netlight.dimlight.objects;

import java.util.Date;

public class Message {
	private int id;
	private String content;
	private Date created;
	private Date read;

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
}
