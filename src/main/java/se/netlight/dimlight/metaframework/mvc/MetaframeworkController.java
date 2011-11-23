package se.netlight.dimlight.metaframework.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import se.netlight.dimlight.controller.AbstractDimlightController;
import se.netlight.dimlight.metaframework.MetaframeworkManager;
import se.netlight.dimlight.metaframework.mvc.beans.MetaFrameworkBean;
import se.netlight.dimlight.metaframework.session.DimlightMetaframeworkSessionManager;

@Controller
@RequestMapping("/dimlight")
public class MetaframeworkController extends AbstractDimlightController {
	public MetaframeworkController() {
	}
	
	@RequestMapping("/metaframework.do")
	public ModelAndView index() {
		return new ModelAndView("metaframework", "data", new MetaFrameworkBean());
	}
	
	@RequestMapping("/changemetavalue.do")
	public ModelAndView changeMetaValue(@RequestParam("category") String category, @RequestParam("implementation") String implementation) {
		MetaframeworkManager.getInstance().checkSelectedImplementation(category, implementation);
		DimlightMetaframeworkSessionManager.getCurrentSession().setContext(category, implementation);
		return new ModelAndView("metaframework", "data", new MetaFrameworkBean());
	}
}
