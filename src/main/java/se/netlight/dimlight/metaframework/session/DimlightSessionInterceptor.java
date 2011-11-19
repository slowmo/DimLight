package se.netlight.dimlight.metaframework.session;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class DimlightSessionInterceptor implements HandlerInterceptor {
	private static final String COOKIE_NAME = "DIMLIGHT_METAFRAMEWORK_TOKEN:DO:NOT:HACK";

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
		for (Cookie c : cookies) {
			if (c.getName().equals(COOKIE_NAME))
				token = c.getValue();
		}
		if (token != null) {
			DimlightSessionManager.getInstance().announceSession(token);
		} else {
			token = DimlightSessionManager.getInstance().announceNewSession();
			arg1.addCookie(new Cookie(COOKIE_NAME, token));
		}			
		return true;
	}

}
