<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/taglibs.jspf" %>
<stripes:layout-definition>
   <html>
		<head>
			<title>${pageTitle}</title>
			<style>
			<%@ include file="/WEB-INF/resources/dimlight.css" %> <!-- sorry, Spring MVC is worthless when it comes to loading static resources -->
			</style>
			<c:set var="user"><% session.getAttribute("user"); %></c:set>
		</head>
		<body>
		<div class="MainArea">
			<stripes:layout-render name="/WEB-INF/layout/navigation.jsp"/>
            <stripes:layout-component name="header"/>
			<stripes:layout-component name="contents"/>

            <stripes:layout-component name="footer">
			<!-- NOTHING HERE YET -->
            </stripes:layout-component>
          </div>
        </body>
    </html>
</stripes:layout-definition>