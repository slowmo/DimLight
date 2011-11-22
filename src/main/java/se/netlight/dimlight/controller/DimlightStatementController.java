package se.netlight.dimlight.controller;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import se.netlight.dimlight.dao.DAOException;
import se.netlight.dimlight.dao.ProvidedInteger;
import se.netlight.dimlight.model.UserWrapper;
import se.netlight.dimlight.objects.Bet;
import se.netlight.dimlight.objects.Message;
import se.netlight.dimlight.objects.Statement;
import se.netlight.dimlight.objects.User;

@Controller
@RequestMapping("/dimlight")
public class DimlightStatementController extends AbstractDimlightController {
	private static final double PRICE_POOL_ANTE = 0.95;
	private Logger logger = Logger.getLogger(DimlightStatementController.class);

	@RequestMapping("/closestatement.do")
	public ModelAndView closeStatement(@RequestParam("id") String id,
			@RequestParam("outcome") String outcome, HttpSession session) {
		boolean positive = outcome.equals("1");

		try {
			Statement statement = getDao().getStatementForId(
					wrapNumericParameter(id));
			if (statement == null)
				throw new RuntimeException("Unknown statement with id " + id);

			// gather the price pool
			List<Bet> betsForStatement = getDao().getBetsForStatement(
					wrapNumericParameter(id));
			double pricePool = 0, wonPrice = 0;
			for (Bet bet : betsForStatement) {
				pricePool += bet.getAmount();
				if (bet.isPositive() == positive)
					wonPrice += bet.getAmount();
			}

			// remove the ante
			pricePool *= PRICE_POOL_ANTE;

			// pay out the results
			for (Bet bet : betsForStatement) {
				if (bet.isPositive() == positive) {
					double payoutFraction = bet.getAmount() / wonPrice;
					int payout = (int) (pricePool * payoutFraction);
					User u = bet.getUser();
					getDao().changeUserBalance(u,
							ProvidedInteger.wrap(u.getBalance() + payout));
					logger.info("Paying out " + payout + " to " + u.getName());

					getDao().saveMessage(
							new Message("Congratulations, your bet for \""
									+ statement.getName() + "\" brought you "
									+ payout + " winnings", bet.getUser()));
				} else {
					getDao().saveMessage(
							new Message("Sorry, your bet for \""
									+ statement.getName()
									+ "\" did not work out.", bet.getUser()));
				}
			}

			// close the statement
			statement.setPositiveOutcome(positive);
			statement.setResolved(new Date());
			getDao().saveStatement(statement);
		} catch (DAOException e) {
			throw new RuntimeException("Failed to save user: ", e);
		}

		return buildUserModel(session);
	}

	@RequestMapping("/statements.do")
	public ModelAndView statements(HttpSession session) {
		return buildUserModel(session);
	}

	@RequestMapping("/placeBet.do")
	public ModelAndView placeBet(@RequestParam("id") String id,
			@RequestParam("amount") String amount,
			@RequestParam("bet") String bet, HttpSession session)
			throws DAOException {
		boolean betOnSuccess = bet.endsWith("success");
		Statement s = getDao().getStatementForId(wrapNumericParameter(id));
		if (s == null)
			throw new RuntimeException("Unknown statement for id " + id);

		User u = loadUser(session, true);

		if (u.getBalance() < Integer.parseInt(amount))
			throw new RuntimeException("Insufficient balance");
		
		getDao().storeBet(s, u, betOnSuccess, wrapNumericParameter(amount));
		getDao().changeUserBalance(u,
				ProvidedInteger.wrap(u.getBalance() - Integer.parseInt(amount)));

		return buildUserModel(session);
	}

	@RequestMapping("/addStatement.do")
	public ModelAndView addStatement(@RequestParam("name") String name,
			@RequestParam("description") String description, HttpSession session)
			throws DAOException {
		Statement s = new Statement(name, description, loadUser(session, true));
		getDao().saveStatement(s);

		return buildUserModel(session);
	}

	private ModelAndView buildUserModel(HttpSession session) {
		ModelAndView mav = new ModelAndView("statement");
		User user = loadUser(session, false);
		try {
			mav.addObject("openStatements", getDao().getOpenStatements());
			if (user != null)
				mav.addObject("statements",
						getDao().getOpenStatementsForUser(user));
			else
				mav.addObject("statements", Collections.emptyList());
			mav.addObject("user", new UserWrapper(user));
		} catch (DAOException e) {
			throw new RuntimeException(e);
		}
		return mav;
	}

	@RequestMapping("/showyourbets.do")
	public ModelAndView showYourBets(
			@RequestParam("statement") String statement, HttpSession session)
			throws DAOException {
		List<Bet> bets = getDao().getBetsForStatementAndUser(
				wrapNumericParameter(statement), loadUser(session, true));
		Statement s = getDao().getStatementForId(
				wrapNumericParameter(statement));
		return buildBetsModel(bets, s, true);
	}

	@RequestMapping("/showbets.do")
	public ModelAndView showStatementBets(
			@RequestParam("statement") String statement, HttpSession session)
			throws DAOException {
		List<Bet> bets = getDao().getBetsForStatement(
				wrapNumericParameter(statement));
		Statement s = getDao().getStatementForId(
				wrapNumericParameter(statement));
		return buildBetsModel(bets, s, false);
	}

	private ModelAndView buildBetsModel(List<Bet> bets, Statement statement,
			boolean currentUser) {
		ModelAndView mav = new ModelAndView("bets");
		mav.addObject("bets", bets);
		mav.addObject("statement", statement);
		mav.addObject("currentUser", currentUser);
		return mav;
	}

}
