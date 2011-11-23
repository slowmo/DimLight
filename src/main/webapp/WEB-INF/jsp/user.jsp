<%@ include file="/WEB-INF/taglibs.jspf"%>
<stripes:layout-render name="/WEB-INF/layout/default.jsp" pageTitle="Statements">
	<stripes:layout-component name="header">
		<h2><c:out value="${pageuser.name}"/></h2>
		<div>
			<h3>Statements by this user</h3>
			<ul>
			<c:forEach var="statement" items="${statements}">
				<li>
				<div style="float:left;">
				<div class="caption">
					<span><c:out value="${statement.name}"/></span>
				</div>
				<div class="description">
					<span><c:out value="${statement.description}"/></span>
				</div>
				<div class="data">
					<span>created <c:out value="${statement.created}"/> </span>
					<a href="makebet.do?id=<c:out value="${statement.id}"/>"></a>
				</div>
				</div>
				<c:if test="${user != null}">
					<div style="float:left;">
					<form method="post" action="placeBet.do">
					<input type="text" name="amount"/>
					<input type="hidden" name="id" value="<c:out value="${statement.id}"/>"/>				
					<input type="submit" value="Bet on success" name="bet"/>
					<input type="submit" value="Bet on failure" name="bet"/>
					</form>
					<a href="showyourbets.do?statement=<c:out value="${statement.id}"/>">Show your bets on this statement</a>
					</div>					
				</c:if>
				<div style="clear:both;"></div>
				</li>			
			</c:forEach>
			</ul>
		</div>
		<c:if test="${user != null}">
			<div>
				<h3>Send this user a message</h3>
				<form method="post" action="addmessage.do">
				<input type="hidden" name="recipient" value="<c:out value="${pageuser.id}"/>"/>
				<textarea name="message"></textarea>
				<input type="submit" value="Send message"/>
				</form>
			</div>
		</c:if>
	</stripes:layout-component>
</stripes:layout-render>


