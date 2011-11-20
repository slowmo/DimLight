<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
	<head>
		<style>
		<%@ include file="/WEB-INF/resources/dimlight.css" %> <!-- sorry - Spring MVC is worthless when it comes to loading static resources -->
		</style>
	</head>
<body>
	<div class="MainArea">
		<h2>DimLight</h2>
		<div>
		Welcome to Dimlight<c:if test="${data.loggedin}">, <c:out value="${data.user.name}"/></c:if>! Here, you can make crazy claims and have your colleagues take bets on them! 
		</div>
		<div>
		<h3>The latest claims:</h3>
		<ul class="claims">		
		<c:forEach var="statement" items="${data.statements}">
			<li>
			<div class="caption">
				<span><c:out value="${statement.name}"/></span>
			</div>
			<div class="description">
				<span><c:out value="${statement.description}"/></span>
			</div>
			<div class="data">
				<span>by <c:out value="${statement.creator.name}"/> - created <c:out value="${statement.created}"/> </span>
			</div>
			</li>
		</c:forEach>
		</ul>		
		</div>
		<c:if test="${data.loggedin == false}">
		<div class="loginframe">
		<h3>Login:</h3>
		<dimlight:login></dimlight:login>
		<c:if test="${data.errorSet}">
			<div class="error"><c:out value="${data.error}" /></div>
		</c:if>
		</div>		
		</c:if>
		<c:if test="${data.loggedin}">
			<a href="profile.do">profile page</a> - <a href="statements.do">statements</a> - <a href="logout.do">log out</a> 
		</c:if>		
	</div>
	</body>
</html>
