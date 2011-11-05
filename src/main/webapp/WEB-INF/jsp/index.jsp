

<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
<body>
<h2><c:out value="${testBean.message}"/></h2>
${param.textBean.message}<p/> 
<%= new java.util.Date().toString() %><br/>
</body>
</html>
