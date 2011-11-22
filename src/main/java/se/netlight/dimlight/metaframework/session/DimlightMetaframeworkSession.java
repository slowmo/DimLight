package se.netlight.dimlight.metaframework.session;

import java.util.HashMap;


public class DimlightMetaframeworkSession {
	private long lastAccess;
	private String token;
	private HashMap<String, String> contexts;
	
	public DimlightMetaframeworkSession(String token) {
		this.token = token;
		this.contexts = new HashMap<String, String>();
	}
	
	public String getToken() {
		return token;
	}

	public long getLastAccess() {
		return lastAccess;
	}

	public void touch() {
		lastAccess = System.currentTimeMillis();
	}
	
	public String getContext(String context) {
		return contexts.get(context);
	}
	
	public void setContext(String context, String value) {
		contexts.put(context, value);
	}

}
