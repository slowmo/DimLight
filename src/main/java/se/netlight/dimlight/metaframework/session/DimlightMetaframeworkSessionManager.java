package se.netlight.dimlight.metaframework.session;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

public class DimlightMetaframeworkSessionManager {	
	private static final DimlightMetaframeworkSessionManager instance = new DimlightMetaframeworkSessionManager();
	private static final String SERVER_SECRET = "Stop! Hammertime";
	private static final long SESSION_TIMEOUT = 60 * 60 * 1000;  //  one hour
	private LinkedHashMap<String, DimlightMetaframeworkSession> sessions;
	private ThreadLocal<DimlightMetaframeworkSession> currentSession;
	private Logger logger = Logger.getLogger(DimlightMetaframeworkSessionManager.class); 
	
	private DimlightMetaframeworkSessionManager() {
		sessions = new LinkedHashMap<String, DimlightMetaframeworkSession>();
		currentSession = new ThreadLocal<DimlightMetaframeworkSession>();
	}
	
	public static DimlightMetaframeworkSessionManager getInstance() {
		return instance;
	}

	public synchronized void announceSession(String token) {		
		DimlightMetaframeworkSession session = sessions.get(token);
		if (session == null) {
			session = new DimlightMetaframeworkSession(token);
			sessions.put(token, session);
			logger.info("Session for token " + token + " not found, creating");
		}
		logger.debug("Session for token " + token + " found");
		session.touch();
		currentSession.set(session);
		
		// remove dead sessions
		long now = System.currentTimeMillis();
		for (Iterator<Entry<String, DimlightMetaframeworkSession>> it = sessions.entrySet().iterator(); it.hasNext(); ) {
			Entry<String, DimlightMetaframeworkSession> next = it.next();
			if (now - next.getValue().getLastAccess() > SESSION_TIMEOUT) {
				logger.info("Removing old session " + next.getValue().getToken());
				it.remove();
			}				
		}
	}

	public static String getHexString(byte[] b) {
		  String result = "";
		  for (int i=0; i < b.length; i++) {
		    result +=
		          Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
		  }
		  return result;
		}
	
	public String announceNewSession() {
		try {
			// generate the token
			MessageDigest digest = MessageDigest.getInstance("MD5");
			String clear = Calendar.getInstance().getTimeInMillis() + ":" + SERVER_SECRET;
			byte[] calculated = digest.digest(clear.getBytes());
			String token = getHexString(calculated);
			
			// use it to generate a new session
			announceSession(token);
			return token;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Server configuration failure; fatal", e);
		}		
	}
	
	public static DimlightMetaframeworkSession getCurrentSession() {
		return instance.currentSession.get();
	}
}
