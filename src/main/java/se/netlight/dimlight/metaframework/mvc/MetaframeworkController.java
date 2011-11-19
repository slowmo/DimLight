package se.netlight.dimlight.metaframework.mvc;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import se.netlight.dimlight.controller.AbstractDimlightController;

@Controller
@RequestMapping("/meta")
public class MetaframeworkController extends AbstractDimlightController {
	public MetaframeworkController() {
	}
	
	@RequestMapping("/test.do")
	public ModelAndView testInvokation() {
		return new ModelAndView("index", "statusMessageKey", new Date());
	}
	
    @RequestMapping(value = "/compare", method = RequestMethod.GET)
    public ModelAndView compare(@RequestParam("left") String left,
            @RequestParam("right") String right) {

    	ModelAndView mav = new ModelAndView("index");
        return mav;
    }
    
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView mav = new ModelAndView("index");
		
		return mav;
	}
}
