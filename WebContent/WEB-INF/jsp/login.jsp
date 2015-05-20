<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page session="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style_new.css"/>"  media="screen" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style_response.css"/>"  media="screen" />
<title>Welcome to the Mobile Purchasing Survey, G'day mate</title>
</head>
<body>
<div class="widget-box">
	<div class="widget-header">
	<h5 class="widget-title">Welcome to E-Commerce System, G'day mate</h5>
	</div>
	<div class="widget-body">
		<div class="widget-main">
			<c:url var="loginUrl" value="/j_spring_security_check" />
			<form action="${loginUrl}"  method="post" >
				<p>Username</p>
				<input type="text"  class="input-small" id="username" name="username" >
				<p>Password</p>
				<input type="password"  class="input-small" id="password" name="password" >
				<input type="submit" class="btn btn-info btn-sm" value="Login">
			</form>
		</div>
	</div>
</div>
</body>
</html>