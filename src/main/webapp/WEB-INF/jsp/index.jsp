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
							<span>by <c:out value="${statement.creator.name}" /> -
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
	</stripes:layout-component>
</stripes:layout-render>
