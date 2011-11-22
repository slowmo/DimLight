<%@ include file="/WEB-INF/taglibs.jspf"%>
<stripes:layout-render name="/WEB-INF/layout/default.jsp" pageName="Profile">
	<stripes:layout-component name="header">
		<h2>
			Profile page for
			<c:out value="${ user.name }" />
		</h2>
		<div>
			Your account balance is:
			<c:out value="${ user.balance }" />
			.<br /> Increase it: <a href="charge.do?amount=100">100 SEK</a> - <a
				href="charge.do?amount=500">500 SEK</a> - <a
				href="charge.do?amount=1000">1000 SEK</a> <span class="hint">(In
				reality, this would be withdrawn from your credit card.)</span>
		</div>
	</stripes:layout-component>
	<stripes:layout-component name="contents">
		<div>
			<form method="post" action="changesecret.do">
				Your secret: <input type="text" name="secret"
					value="<c:out value="${ user.secret }"/>" /><input type="submit"
					value="Change it!" />
			</form>
		</div>
		<div>
			<h2>Your bets</h2>
			<ul>
				<c:forEach var="bet" items="${ bets }">
					<li><c:out value="${ bet.statement.name }" />: you bet <b><c:out
								value="${ bet.amount }" /></b> on <c:choose>
							<c:when test=" ${bet.positive }">success.</c:when>
							<c:otherwise>failure.</c:otherwise>
						</c:choose></li>
				</c:forEach>
			</ul>
		</div>
		<div>
			<h2>Your messages</h2>
			<ul>
				<c:forEach var="message" items="${ messages }">
					<li><c:out value="${ message.content }" /> <span class="hint"><c:out
								value="${ message.created }" />(<a
							href="readmessage.do?id=<c:out value="${ message.id}"/>">mark
								read</a>)</span></li>
				</c:forEach>
			</ul>
		</div>
	</stripes:layout-component>
</stripes:layout-render>
