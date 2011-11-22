<%@ include file="/WEB-INF/taglibs.jspf"%>
<stripes:layout-render name="/WEB-INF/layout/default.jsp" pageTitle="Statements">
	<stripes:layout-component name="contents">
		<h2>Open statements</h2>
		<div>
			<ul>
<<<<<<< HEAD
				<c:forEach var="statement" items="${openStatements}">
					<li>
						<div style="float: left;">
							<div class="caption">
								<span><c:out value="${statement.name}" /></span>
							</div>
							<div class="description">
								<span><c:out value="${statement.description}" /></span>
							</div>
							<div class="data">
								<span>by <c:out value="${statement.creator.name}" /> -
									created <c:out value="${statement.created}" />
								</span> <a href="makebet.do?id=<c:out value="${statement.id}"/>"></a>
							</div>
						</div>
						<div style="float: left;">
							<form method="post" action="placeBet.do">
								<input type="text" name="amount" /> <input type="hidden"
									name="id" value="<c:out value="${statement.id}"/>" /> <input
									type="submit" value="Bet on success" name="bet" /> <input
									type="submit" value="Bet on failure" name="bet" />
							</form>
						</div>
						<div style="clear: both;"></div>
					</li>
				</c:forEach>
			</ul>
		</div>
		<c:if test="${user.loggedin}">
			<h3>Your statements</h3>
			<ul>
				<c:forEach var="statement" items="${statements}">
					<li>
						<div class="caption">
							<span><c:out value="${statement.name}" /></span>
						</div>
						<div class="description">
							<span><c:out value="${statement.description}" /></span>
						</div>
						<div class="data">
							<span>created <c:out value="${statement.created}" /></span> - <a
								href="closestatement.do?id=<c:out value="${statement.id}"/>&outcome=1">Close
								as successful</a> - <a
								href="closestatement.do?id=<c:out value="${statement.id}"/>&outcome=0">Close
								as failed</a>
						</div>
					</li>
				</c:forEach>
			</ul>
			<h3>Create a statement</h3>
			<form action="addStatement.do" method="post">
				<div>
					Name: <input name="name" type="text" />
				</div>
				<div>
					Description:
					<textarea name="description"></textarea>
				</div>
				<div>
					<input type="submit" value="Create!" />
				</div>
			</form>
=======
			<c:forEach var="statement" items="${openStatements}">
				<li>
				<div style="float:left;">
				<div class="caption">
					<span><c:out value="${statement.name}"/></span>
				</div>
				<div class="description">
					<span><c:out value="${statement.description}"/></span>
				</div>
				<div class="data">
					<span>by <c:out value="${statement.creator.name}"/> - created <c:out value="${statement.created}"/> </span>
					<a href="makebet.do?id=<c:out value="${statement.id}"/>"></a>
				</div>
				</div>
				<div style="float:left;">
				<form method="post" action="placeBet.do">
				<input type="text" name="amount"/>
				<input type="hidden" name="id" value="<c:out value="${statement.id}"/>"/>				
				<input type="submit" value="Bet on success" name="bet"/>
				<input type="submit" value="Bet on failure" name="bet"/>
				</form>
				<a href="showyourbets.do?statement=<c:out value="${statement.id}"/>">Show your bets on this statement</a>
				</div>
				<div style="clear:both;"></div>
				</li>
			</c:forEach>
			</ul>
		</div>
		<c:if test="${user.loggedin}">
		<h3>Your statements</h3>
		<ul>
		<c:forEach var="statement" items="${statements}">
			<li>
			<div class="caption">
				<span><c:out value="${statement.name}"/></span>
			</div>
			<div class="description">
				<span><c:out value="${statement.description}"/></span>
			</div>
			<div class="data">
				<span>created <c:out value="${statement.created}"/></span>
				<div>
				<a href="closestatement.do?id=<c:out value="${statement.id}"/>&outcome=1">Close as successful</a> - 
				<a href="closestatement.do?id=<c:out value="${statement.id}"/>&outcome=0">Close as failed</a> - 
				<a href="showbets.do?statement=<c:out value="${statement.id}"/>">Show bets on this statement</a>
				</div>
			</div>
			</li>
		</c:forEach>
		</ul>
		<h3>Create a statement</h3>
		<form action="addStatement.do" method="post">
		<div>Name: <input name="name" type="text"/></div>
		<div>Description: <textarea name="description"></textarea></div>
		<div><input type="submit" value="Create!"/></div>
		</form>
>>>>>>> upstream/master
		</c:if>
	</stripes:layout-component>
</stripes:layout-render>


