package se.netlight.dimlight.metaframework.session;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class DimlightMetaframeworkSessionInterceptor implements HandlerInterceptor {
	private static final String COOKIE_NAME = "DIMLIGHTMETAFRAMEWORKTOKEN";

	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}

	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		Cookie[] cookies = arg0.getCookies();
		String token = null;
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals(COOKIE_NAME))
					token = c.getValue();
			}
		}
		if (token != null) {
			DimlightMetaframeworkSessionManager.getInstance().announceSession(token);
		} else {
			token = DimlightMetaframeworkSessionManager.getInstance().announceNewSession();
			Cookie c = new Cookie(COOKIE_NAME, token);
			c.setPath("/");
			arg1.addCookie(c);
		}			
		return true;
	}

}
