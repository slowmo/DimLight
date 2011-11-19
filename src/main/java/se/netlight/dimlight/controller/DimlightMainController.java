package se.netlight.dimlight.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import se.netlight.dimlight.dao.DAOException;
import se.netlight.dimlight.dao.IDimlightDAO;
import se.netlight.dimlight.model.IndexViewBean;
import se.netlight.dimlight.objects.Statement;
import se.netlight.dimlight.objects.User;

@Controller
@RequestMapping("/dimlight")
public class DimlightMainController extends AbstractDimlightController {
	@RequestMapping("/index.do")
	public ModelAndView index(HttpSession session) {
		IDimlightDAO dao = getDao();
		List<Statement> lastStatements = dao.getLastStatements(3);
		
		return new ModelAndView("index", "data", new IndexViewBean(lastStatements, (User) session.getAttribute("user")));
	}
	
	@RequestMapping("/login.do")
	public ModelAndView login(@RequestParam("name") String username, @RequestParam("password") String password, HttpSession session) {
		IDimlightDAO dao = getDao();
		List<Statement> lastStatements = dao.getLastStatements(3);		
		IndexViewBean bean = new IndexViewBean(lastStatements, null);
		try {
			User user = dao.getUserForName(username);
			if (user != null) {
				if (user.getPassword().equals(password)) {
					session.setAttribute("user", user);
					bean.setUser(user);
				} else {
					bean.setError("Wrong password");
				}
			} else {
				bean.setError("Unknown user " + username);
			}
		} catch (DAOException e) {
			throw new RuntimeException("Failed to load user: ", e);
		}

		return new ModelAndView("index", "data", bean);
	}	
	
	@RequestMapping("/logout.do")
	public ModelAndView logout(HttpSession session) {
		session.setAttribute("user", null);
		return index(session);
	}
	
	@RequestMapping("/profile.do")
	public ModelAndView profile(HttpSession session) {
		User u = (User) session.getAttribute("user");
		if (u == null)
			throw new RuntimeException("Need to be logged in for this page");
		
		return new ModelAndView("profile", "user", u);
	}
	
	@RequestMapping("/charge.do")
	public ModelAndView charge(@RequestParam("amount") String amount, HttpSession session) {
		User u = (User) session.getAttribute("user");
		if (u == null)
			throw new RuntimeException("Need to be logged in for this page");
		
		double balance = u.getBalance();
		balance += Integer.parseInt(amount);
		u.setBalance(balance);
		try {			
			getDao().saveUser(u);
		} catch (DAOException e) {
			throw new RuntimeException("Failed to save user: ", e);
		}
		
		return new ModelAndView("profile", "user", u);
	}
	
	@RequestMapping("/changesecret.do")
	public ModelAndView changeSecret(@RequestParam("secret") String secret, HttpSession session) {
		User u = (User) session.getAttribute("user");
		if (u == null)
			throw new RuntimeException("Need to be logged in for this page");
		
		u.setSecret(secret);
		try {			
			getDao().saveUser(u);
		} catch (DAOException e) {
			throw new RuntimeException("Failed to save user: ", e);
		}
		
		return new ModelAndView("profile", "user", u);
	}
	
	
}
