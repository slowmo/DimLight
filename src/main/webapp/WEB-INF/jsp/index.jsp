<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
<body>
<h2><c:out value="${textBean.message}"/></h2>
${textBean.message}<p/>
<%= new java.util.Date().toString() %><br/>
</body>
</html>
