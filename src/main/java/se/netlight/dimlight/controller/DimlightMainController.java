package se.netlight.dimlight.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import se.netlight.dimlight.dao.DAOException;
import se.netlight.dimlight.dao.IDimlightDAO;
import se.netlight.dimlight.dao.ProvidedInteger;
import se.netlight.dimlight.model.IndexViewBean;
import se.netlight.dimlight.objects.Message;
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
		return buildUserModel(session);
	}
	

	@RequestMapping("/charge.do")
	public ModelAndView charge(@RequestParam("amount") String amount, HttpSession session) {
		User u = loadUser(session, true);
				
		ProvidedInteger balance = wrapNumericParameter(amount);
		
		int oldBalance = u.getBalance();
		
		balance = balance.add(oldBalance);
		try {			
			getDao().changeUserBalance(u, balance);
		} catch (DAOException e) {
			throw new RuntimeException("Failed to save user: ", e);
		}
		
		return buildUserModel(session);
	}
	
	private ModelAndView buildUserModel(HttpSession session) {
		User u = loadUser(session, true);
		ModelAndView ret = new ModelAndView("profile", "user", u);
		try {
			ret.addObject("messages", getDao().getOpenMessagesForUser(u));
			ret.addObject("bets", getDao().getOpenBetsForUser(u));			
		} catch (DAOException e) {
			throw new RuntimeException(e);
		}
		return ret;
	}

	@RequestMapping("/changesecret.do")
	public ModelAndView changeSecret(@RequestParam("secret") String secret, HttpSession session) {
		User u = loadUser(session, true);
				
		try {			
			getDao().changeUserSecret(u, secret);
		} catch (DAOException e) {
			throw new RuntimeException("Failed to save user: ", e);
		}
		
		return buildUserModel(session);
	}
	
	@RequestMapping("/readmessage.do")
	public ModelAndView markMessageRead(@RequestParam("id") String id, HttpSession session) {
		try {			
			ProvidedInteger idObj = wrapNumericParameter(id);
			getDao().markMessageRead(idObj);
		} catch (DAOException e) {
			throw new RuntimeException("Failed to save user: ", e);
		}		
		return buildUserModel(session);
	}
	
	@RequestMapping("/user.do")
	public ModelAndView user(@RequestParam("id") String id, HttpSession session) throws DAOException {
		ModelAndView mav = new ModelAndView("user");
		User user = getDao().getUserForId(wrapNumericParameter(id));
		if (user == null)
			throw new RuntimeException("Unknown user for id " + id);
		mav.addObject("pageuser", user);
		List<Statement> statementsForUser = getDao().getStatementsForUser(user);
		mav.addObject("statements", statementsForUser);
		return mav;
	}
	
	@RequestMapping("/addmessage.do")
	public ModelAndView addMessage(@RequestParam("recipient") String id, @RequestParam("message") String message, HttpSession session) throws DAOException {
		User sender = loadUser(session, true); // make sure user is logged in 
		User recipient = getDao().getUserForId(wrapNumericParameter(id));
		if (recipient == null)
			throw new RuntimeException("Unknown user for id " + id);
	
		Message m = new Message("Message from " + sender.getName() + ": " + message, recipient);
		getDao().saveMessage(m);
		
		return user(id, session);
	}
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleDAOException(Exception ex, HttpServletRequest request) throws Exception {		
		return ErrorHandler.handleException(ex);
	}
}

