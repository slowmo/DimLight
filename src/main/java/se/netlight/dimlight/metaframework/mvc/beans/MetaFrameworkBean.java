package se.netlight.dimlight.metaframework.mvc.beans;

import java.io.Serializable;

public class MetaFrameworkBean implements Serializable {
	private static final long serialVersionUID = -7721316684864969398L;
	private String message;

	public MetaFrameworkBean(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
