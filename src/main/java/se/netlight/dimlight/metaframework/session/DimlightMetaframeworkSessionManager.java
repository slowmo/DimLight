package se.netlight.dimlight.metaframework.session;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class DimlightMetaframeworkSessionManager {	
	private static final DimlightMetaframeworkSessionManager instance = new DimlightMetaframeworkSessionManager();
	private static final String SERVER_SECRET = "Stop! Hammertime";
	private ThreadLocal<DimlightMetaframeworkSession> currentSession;
	private Logger logger = Logger.getLogger(DimlightMetaframeworkSessionManager.class); 
	
	private DimlightMetaframeworkSessionManager() {
		currentSession = new ThreadLocal<DimlightMetaframeworkSession>();
	}
	
	public static DimlightMetaframeworkSessionManager getInstance() {
		return instance;
	}

	private byte[] calculateSignature(String configuration, String token) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			String clear = configuration + ":" + token + ":" + SERVER_SECRET;
			return digest.digest(clear.getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Server configuration failure; fatal", e);
		}		
	}
	
	public synchronized void announceSession(String configuration) {
		DimlightMetaframeworkSession session;
		try {
			// configuration: expected triplet: (unique token, configuration settings, signature)

			String config = "";
			StringTokenizer st = new StringTokenizer(configuration, "!");
			if (!configuration.startsWith("!"))
			{
				// thank you Java, for your crappy tokenizer implementation
				if (st.countTokens() != 3) {
					throw new InvalidSessionException("three tokens expected, got " + st.countTokens());
				}
				config = st.nextToken();
			} else {
				if (st.countTokens() != 2) {
					throw new InvalidSessionException("three tokens expected, got " + st.countTokens());
				}				
			}
			
			String token = st.nextToken();
			String signature = st.nextToken();
	
			// check the signature
			byte[] sigraw = calculateSignature(config, token);
			String sig = getHexString(sigraw);
			if (!sig.equals(signature)) {
				throw new InvalidSessionException("Non-matching signature, got " + sig + ", expected " + signature);
			}
			
			Map<String, String> configMap = new HashMap<String, String>();
			StringTokenizer t = new StringTokenizer(config, "?");
			while (t.hasMoreTokens()) {
				StringTokenizer p = new StringTokenizer(t.nextToken(), "=");
				if (p.countTokens() != 2)
					throw new InvalidSessionException("Malformed configuration");
				String k = p.nextToken();
				String v = p.nextToken();
				
				configMap.put(k, v);
			}
					
			session = new DimlightMetaframeworkSession(configMap, token);
		} catch (InvalidSessionException e) {
			logger.warn("Found an invalid session for content " + configuration, e);
			session = new DimlightMetaframeworkSession(generateNewToken());
		}
		currentSession.set(session);
	}

	public static String getHexString(byte[] b) {
		  String result = "";
		  for (int i=0; i < b.length; i++) {
		    result +=
		          Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
		  }
		  return result;
	}
	
	public void announceNewSession() {
		DimlightMetaframeworkSession session = new DimlightMetaframeworkSession(generateNewToken());
		currentSession.set(session);
	}

	private String generateNewToken() {		
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		String clear = Calendar.getInstance().getTimeInMillis() + ":" + SERVER_SECRET;
		byte[] calculated = digest.digest(clear.getBytes());
		String token = getHexString(calculated);
		return token;
	}
	
	public static DimlightMetaframeworkSession getCurrentSession() {
		return instance.currentSession.get();
	}

	public String serializeSession(DimlightMetaframeworkSession session) {
		// build the key-value pairs of the configuration
		StringBuilder sb = new StringBuilder();
		Map<String, String> contexts = session.getContexts();
		String delim = "";
		for (Map.Entry<String, String> e : contexts.entrySet()) {
			sb.append(delim).append(e.getKey()).append("=").append(e.getValue());
			delim = "?";
		}
		
		String signature = getHexString(calculateSignature(sb.toString(), session.getToken()));
		sb.append("!").append(session.getToken());
		sb.append("!").append(signature);
		return sb.toString();
	}
}
