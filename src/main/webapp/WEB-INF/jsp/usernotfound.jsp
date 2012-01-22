<%@ include file="/WEB-INF/taglibs.jspf"%>
<stripes:layout-render name="/WEB-INF/layout/default.jsp" pageTitle="Statements">
	<stripes:layout-component name="header">
		<h2>User could not be found</h2>
		<div>
		We are sorry, but there is no user named ${username} in our database.
		</div>
	</stripes:layout-component>
</stripes:layout-render>


