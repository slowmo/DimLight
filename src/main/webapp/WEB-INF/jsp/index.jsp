<%@ include file="/WEB-INF/taglibs.jspf"%>
<stripes:layout-render name="/WEB-INF/layout/default.jsp" pageTitle="Start">
	<stripes:layout-component name="header">
		<h2>DimLight</h2>
		<div>
			Welcome to Dimlight
			<c:if test="${user != null}">, <c:out
					value="${user.name}" />
			</c:if>
			! Here, you can make crazy claims and have your colleagues take bets
			on them!
		</div>
	</stripes:layout-component>
	<stripes:layout-component name="contents">
		<div>
			<h3>The latest claims:</h3>
			<ul class="claims">
				<c:forEach var="statement" items="${data.statements}">
					<li>
						<div class="caption">
							<span><c:out value="${statement.name}" /></span>
						</div>
						<div class="description">
							<span><c:out value="${statement.description}" /></span>
						</div>
						<div class="data">
							<span>by <a href="user.do?id=<c:out value="${statement.creator.id}" />"><c:out value="${statement.creator.name}" /></a> -
								created <c:out value="${statement.created}" />
							</span>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
		<c:if test="${user == null}">
			<div class="loginframe">
				<h3>Login:</h3>
				<dimlight:login></dimlight:login>
				<c:if test="${data.errorSet}">
					<div class="error">
						<c:out value="${data.error}" />
					</div>
				</c:if>
			</div>
		</c:if>
		
		<hr style="margin:8px;"/>
		
		<div>
		<p>Dear Netlight Hacking Group!</p>
		This is Dimlight - an application that is entirely open for various exploits. If you are seeing this page, you succeeded in installing it locally.<p/>
		Dimlight is a webapp where people can make statements, and other people can bet on the outcome. Once the creator sets the outcome to "success" or "failure", the price money
		is given to those that guessed correctly, in proportion to the money the placed on their bet.<p/>
		Dimlight uses a in-memory Hypersonic SQL database, so each time you restart the server, the application will be reset. Which might be exactly what you want to start over again. <p/>
		Today, we are investigating SQL injection, so try to modify and read data by adding SQL code to the request parameters. Here are some suggestions:
		<ul>
			<li>Destroy other user's secret</li>
			<li>Add large amounts of money to your account</li>		
			<li>Steal another user's secret</li>
			<li>Find out who the people are who bet on a statement (hint: you might not be able to do this solely by SQL injection alone)</li>
		</ul>
		Please note that there is little to none sanitation going on. If you want to tighten security, we provide four steps of difficulty (configurable via "dimlight security configuration" once you are logged in):
		<ul>
		<li>Easy: next to no protection.</li>
		<li>Medium: some sanititation, full error display.</li>
		<li>Hard: some sanititation, no error display.</li>
		<li>Impossible: full sanititation, no error display. (If you manage to pull off SQL injection with this one, I am really impressed!)</li>
		</ul> 
		<p/>
		For logging in, here are some accounts you can use:
		<ul>
		<li>Erik</li>
		<li>Johan</li>
		<li>Anders</li>
		<li>Oscar</li>
		</ul>
		Each account has the password "secret".<p/>
		
		Here are two links you might find useful:
		<ul>
		<li><a href="http://hsqldb.org/doc/guide/ch09.html">Hypersonic SQL Reference</a></li>
		<li><a href="http://www.w3schools.com/tags/ref_urlencode.asp">URL Encoding Reference</a></li>
		</ul>
		
		Happy hacking! And please - do not abuse the fact that this app runs locally. That's cheating!
		</div>
	</stripes:layout-component>
</stripes:layout-render>
