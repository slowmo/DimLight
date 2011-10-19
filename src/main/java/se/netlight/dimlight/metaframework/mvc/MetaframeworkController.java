package se.netlight.dimlight.metaframework.mvc;

import java.util.Comparator;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.hsqldb.lib.StringComparator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import se.netlight.dimlight.metaframework.mvc.beans.MetaFrameworkBean;

@Controller
@RequestMapping("/meta")
public class MetaframeworkController {
	private JdbcTemplate template;
	
	public void setDatasource(DataSource source)  {
		template = new JdbcTemplate(source);
	}
	 
	public MetaframeworkController() {
	}
	
	@RequestMapping("/test.do")
	public ModelAndView testInvokation() {
		return new ModelAndView("index", "statusMessageKey", new Date());
	}
	
    @RequestMapping(value = "/compare", method = RequestMethod.GET)
    public ModelAndView compare(@RequestParam("left") String left,
            @RequestParam("right") String right) {
 
        int result = new StringComparator().compare(left, right);
        String textResult = left + " is " + ((result < 0) ? "less than " : (result > 0 ? "greater than " : "equal to ")) + right;
        System.out.println(textResult);
        
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("testBean", new TestBean(textResult));
        return mav;
    }
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		MetaFrameworkBean bean = new MetaFrameworkBean("Hello World");
		int count = template.queryForInt("SELECT COUNT(*) FROM USER");
		System.out.println("Got rows: " + count);

		ModelAndView mav = new ModelAndView("index");
		mav.addObject("message", bean);
		
		return mav;
	}
}
