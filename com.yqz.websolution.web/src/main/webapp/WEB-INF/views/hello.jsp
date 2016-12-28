<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Maven + Spring MVC</title>

<spring:url value="/resources/core/css/hello.css" var="coreCss" />
<spring:url value="/resources/core/css/bootstrap.min.css"
	var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${coreCss}" rel="stylesheet" />
</head>

<div class="jumbotron">
	<div class="container">
		<h1>${title}</h1>
		<p>
			<c:if test="${not empty message}">
			Hello, ${message}
		</c:if>

			<c:if test="${empty message}">
			Welcome Welcome!
		</c:if>
		</p>
		 
	</div>
</div>
 
<div class="container">

	 
	<hr>
	<footer>
		<p>© Mkyong.com 2015</p>
	</footer>
</div>

<spring:url value="/resources/core/css/hello.js" var="coreJs" />
<spring:url value="/resources/core/css/bootstrap.min.js"
	var="bootstrapJs" />

<script src="${coreJs}"></script>
<script src="${bootstrapJs}"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

</body>
</html>