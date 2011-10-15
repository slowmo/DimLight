package se.netlight.dimlight.metaframework.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class MetaframeworkController extends AbstractController {
	public MetaframeworkController() {
	}
	
	
	public ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView mav = new ModelAndView("/jsp/index.jsp");
		mav.addObject("message", "Hello World!");
		return mav;
	}
}
