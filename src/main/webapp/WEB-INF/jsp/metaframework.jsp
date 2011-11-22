<<<<<<< HEAD
<<<<<<< HEAD
<%@ include file="/WEB-INF/taglibs.jspf"%>
<stripes:layout-render name="/WEB-INF/layout/default.jsp"
	pageTitle="Metaframework">
	<stripes:layout-component name="contents">
		<h2>DimLight Meta Framework</h2>
		Here you can select the "security grading" of the application. 
		<c:forEach var="category" items="${data.categories}">
			<div class="category">
				<h3>
					<c:out value="${category.display}" />
				</h3>
				<div>
					<c:out value="${category.description}" />
				</div>
				<div class="selections">
					<form action="changemetavalue.do" method="post">
						<input type="hidden" name="category"
							value="<c:out value="${category.name}"/>" />
						<c:forEach var="implementation"
							items="${category.implementations}">
							<div class="implementation">
								<div style="float: left; padding-right: 8px;">
									<input type="radio" name="implementation"
										value="<c:out value="${implementation.name}"/>"
										<c:if test="${implementation.selected}">checked</c:if> />
								</div>
								<div style="float: left; width: 90%;">
									<div style="font-weight: bold;">
										<c:out value="${implementation.display}" />
									</div>
									<div class="hint">
										<c:out value="${implementation.description}" />
									</div>
								</div>
								<div style="clear: both;"></div>
							</div>
						</c:forEach>
						<input type="submit" value="Change" />
					</form>
				</div>
			</div>
		</c:forEach>
	</stripes:layout-component>
</stripes:layout-render>

