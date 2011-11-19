package se.netlight.dimlight.metaframework.session;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

public class DimlightSessionManager {	
	private static final DimlightSessionManager instance = new DimlightSessionManager();
	private static final String SERVER_SECRET = "Stop! Hammertime";
	private static final long SESSION_TIMEOUT = 60 * 60 * 1000;  //  one hour
	private LinkedHashMap<String, DimlightSession> sessions;
	private ThreadLocal<DimlightSession> currentSession;
	private Logger logger = Logger.getLogger(DimlightSessionManager.class); 
	
	private DimlightSessionManager() {
		sessions = new LinkedHashMap<String, DimlightSession>();
		currentSession = new ThreadLocal<DimlightSession>();
	}
	
	public static DimlightSessionManager getInstance() {
		return instance;
	}

	public synchronized void announceSession(String token) {		
		DimlightSession session = sessions.get(token);
		if (session == null) {
			session = new DimlightSession(token);
			sessions.put(token, session);
			logger.info("Session for token " + token + " not found, creating");
		}
		logger.debug("Session for token " + token + " found");
		session.touch();
		currentSession.set(session);
		
		// remove dead sessions
		long now = System.currentTimeMillis();
		for (Iterator<Entry<String, DimlightSession>> it = sessions.entrySet().iterator(); it.hasNext(); ) {
			Entry<String, DimlightSession> next = it.next();
			if (now - next.getValue().getLastAccess() > SESSION_TIMEOUT) {
				logger.info("Removing old session " + next.getValue().getToken());
				it.remove();
			}				
		}
	}

	public String announceNewSession() {
		try {
			// generate the token
			MessageDigest digest = MessageDigest.getInstance("MD5");
			String clear = Calendar.getInstance().getTimeInMillis() + ":" + SERVER_SECRET;
			byte[] calculated = digest.digest(clear.getBytes());
			String token = URLEncoder.encode(new String(calculated), "UTF-8");
			
			// use it to generate a new session
			announceSession(token);
			return token;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Server configuration failure; fatal", e);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Server configuration failure; fatal", e);
		}		
	}
	
	public static DimlightSession getCurrentSession() {
		return instance.currentSession.get();
	}
}
