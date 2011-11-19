package se.netlight.dimlight.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import se.netlight.dimlight.dao.DAOException;
import se.netlight.dimlight.dao.IDimlightDAO;
import se.netlight.dimlight.dao.ProvidedInteger;
import se.netlight.dimlight.dao.category.AbstractDatabaseCategoryImplementation;
import se.netlight.dimlight.metaframework.MetaframeworkManager;
import se.netlight.dimlight.metaframework.session.DimlightSessionManager;
import se.netlight.dimlight.model.IndexViewBean;
import se.netlight.dimlight.objects.Statement;
import se.netlight.dimlight.objects.User;

@Controller
@RequestMapping("/dimlight")
public class DimlightMainController extends AbstractDimlightController {
	@RequestMapping("/index.do")
	public ModelAndView index(HttpSession session) {
		IDimlightDAO dao = getDao();
		List<Statement> lastStatements = dao.getLastStatements(ProvidedInteger.wrap(3));
		
		return new ModelAndView("index", "data", new IndexViewBean(lastStatements, (User) session.getAttribute("user")));
	}
	
	@RequestMapping("/login.do")
	public ModelAndView login(@RequestParam("name") String username, @RequestParam("password") String password, HttpSession session) {
		IDimlightDAO dao = getDao();
		List<Statement> lastStatements = dao.getLastStatements(ProvidedInteger.wrap(3));		
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
		User u = loadUser(session);
		return new ModelAndView("profile", "user", u);
	}
	
	private User loadUser(HttpSession session) {
		User u = (User) session.getAttribute("user");
		if (u == null)
			throw new RuntimeException("Need to be logged in for this page");

		// reload, to see any freshly hacked data :-)
		try {
			u = getDao().getUserForId(ProvidedInteger.wrap(u.getId()));
		} catch (DAOException e) {
			throw new RuntimeException("Failed to load user", e);
		}
		
		return u;
	}

	@RequestMapping("/charge.do")
	public ModelAndView charge(@RequestParam("amount") String amount, HttpSession session) {
		User u = loadUser(session);
				
		AbstractDatabaseCategoryImplementation daoImpl = MetaframeworkManager.getInstance().getSelectedImplementation("database", DimlightSessionManager.getCurrentSession().getContext("database"));
		ProvidedInteger balance = daoImpl.buildProvidedInteger(amount);
		
		int oldBalance = u.getBalance();
		
		balance = balance.add(oldBalance);
		try {			
			getDao().changeUserBalance(u, balance);
		} catch (DAOException e) {
			throw new RuntimeException("Failed to save user: ", e);
		}
		
		return new ModelAndView("profile", "user", loadUser(session));
	}
	
	@RequestMapping("/changesecret.do")
	public ModelAndView changeSecret(@RequestParam("secret") String secret, HttpSession session) {
		User u = loadUser(session);
				
		try {			
			getDao().changeUserSecret(u, secret);
		} catch (DAOException e) {
			throw new RuntimeException("Failed to save user: ", e);
		}
		
		return new ModelAndView("profile", "user", loadUser(session));
	}
}
