<%@ include file="/WEB-INF/taglibs.jspf"%>
<stripes:layout-render name="/WEB-INF/layout/default.jsp"
	pageTitle="Statements">
	<stripes:layout-component name="contents">
		<h2>
			Bets for
			<c:out value="${statement.name}" />
		</h2>
		<div>
			<c:choose>
				<c:when test="${currentUser}">Showing your bets on this statement. Maybe you did more than one bet.</c:when>
				<c:otherwise>Showing all bets made on this statement, anonymous, of course.</c:otherwise>
			</c:choose>
		</div>
		<div>
			<ul>
				<c:forEach items="${bets}" var="bet">
					<li><c:out value="${bet.created}" />: <b><c:out
								value="${bet.amount}" /></b> bet on <c:out
							value="${bet.positiveString}" /></li>
				</c:forEach>
			</ul>
		</div>
	</stripes:layout-component>
</stripes:layout-render>
