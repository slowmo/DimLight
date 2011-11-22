<%@ include file="/WEB-INF/taglibs.jspf"%>
<stripes:layout-definition>
	<div>
		<c:if test="${user != null}">
			<hr />
			<div>
				<a href="/dimlight/profile.do" >profile
					page<img style="vertical-align: middle;" src="/images/profile.jpg" /></a> :: <a href="/dimlight/statements.do">statements<img style="vertical-align: middle;" src="/images/bets.png" /></a> :: <a
					href=" /dimlight/meta/index.do">security configuration<img style="vertical-align: middle;" src="/images/sec.png" /></a>
				:: <a href="/dimlight/logout.do">log out<img style="vertical-align: middle;" src="/images/exit.png" /></a>
				<hr />
			</div>
		</c:if>

	</div>
</stripes:layout-definition>