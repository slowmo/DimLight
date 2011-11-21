<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
	<head>
		<style>
		<%@ include file="/WEB-INF/resources/dimlight.css" %> 
		</style>
	</head>
<body>
	<div class="MainArea">
		<h2>Profile page for <c:out value="${ user.name }"/></h2>
		<div>
		Your account balance is: <c:out value="${ user.balance }" />.<br/>
		Increase it: <a href="charge.do?amount=100">100 SEK</a> - <a href="charge.do?amount=500">500 SEK</a> - <a href="charge.do?amount=1000">1000 SEK</a>
		<span class="hint">(In reality, this would be withdrawn from your credit card.)</span>
		</div>
		<div>
		<form method="post" action="changesecret.do">
		Your secret: <input type="text" name="secret" value="<c:out value="${ user.secret }"/>"/><input type="submit" value="Change it!"/>		
		</form>
		</div>
	</div>
	</body>
</html>
