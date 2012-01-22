package se.netlight.dimlight.metaframework.session;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class DimlightMetaframeworkSession {
	private long lastAccess;
	private String token;
	private Map<String, String> contexts;
	private boolean dirty;
	
	public DimlightMetaframeworkSession(String token) {
		this.token = token;
		this.contexts = new HashMap<String, String>();
		this.dirty = true;
	}
	
	public DimlightMetaframeworkSession(Map<String, String> configMap,
			String token) {
		this.contexts = configMap;
		this.token = token;
		this.dirty = false;
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
		markDirty();
	}

	public Map<String, String> getContexts() {
		return Collections.unmodifiableMap(contexts); 
	}

	public void markDirty() {
		this.dirty = true;
	}
	
	public boolean isDirty() {
		return dirty;
	}
}
