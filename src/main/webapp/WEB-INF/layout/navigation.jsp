<%@ include file="/WEB-INF/taglibs.jspf"%>
<stripes:layout-definition>
	<div>
		<c:if test="${user != null}">
			<hr />
			<div>
				<a href="profile.do" >profile
					page<img style="vertical-align: middle;" src="../images/profile.jpg" /></a> :: <a href="statements.do">statements<img style="vertical-align: middle;" src="../images/bets.png" /></a> :: <a
					href=" metaframework.do">dimlight security configuration<img style="vertical-align: middle;" src="../images/sec.png" /></a>
				:: <a href="logout.do">log out<img style="vertical-align: middle;" src="../images/exit.png" /></a>
				<hr />
			</div>		
		</c:if>
		<c:if test="${user == null}">
			<div style="float:right; padding:4px;"><a href="index.do">Go to login page</a></div>
			<hr style="clear:both;"/>				
		</c:if>

	</div>
</stripes:layout-definition>