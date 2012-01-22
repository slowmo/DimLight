package se.netlight.dimlight.metaframework.session;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class DimlightMetaframeworkSessionInterceptor implements HandlerInterceptor {
	private static final String COOKIE_NAME = "DIMLIGHTMETAFRAMEWORKTOKEN";
	private Logger logger = Logger.getLogger(DimlightMetaframeworkSessionInterceptor.class); 

	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		DimlightMetaframeworkSession session = DimlightMetaframeworkSessionManager.getCurrentSession();
		if (session.isDirty()) {
			String sessionContent = DimlightMetaframeworkSessionManager.getInstance().serializeSession(session);
			logger.debug("Dirty session, writing cookie with content " + sessionContent);
			Cookie c = new Cookie(COOKIE_NAME, sessionContent);
			c.setPath("/");
			arg1.addCookie(c);
		}
	}

	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		Cookie[] cookies = arg0.getCookies();
		String configuration = null;
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals(COOKIE_NAME))
					configuration = c.getValue();
			}
		}
		if (configuration != null) {
			logger.debug("Found a session, content is " + configuration);
			DimlightMetaframeworkSessionManager.getInstance().announceSession(configuration);
		} else {
			logger.debug("No session found, creating a new one");
			DimlightMetaframeworkSessionManager.getInstance().announceNewSession();
		}			
		return true;
	}

}
