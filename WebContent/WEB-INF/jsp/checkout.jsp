<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Check out</title>
</head>
<body>

<jsp:include page="cart_partial.jsp"></jsp:include>
<form:form action="${pageContext.request.contextPath}/eco/carts/complete" method = "post" commandName="order" >
<label>address 1
<form:input type = "text" path="address1" /> <form:errors path="address1"/></label>
<br/>
<label>address 2
<form:input type = "text" path="address2" /> <form:errors path="address2"/></label>
<br/>
<label>city
<form:input type = "text" path="city" /> <form:errors path="city"/></label>
<br/>
<input id="orderSubmit" type = "submit" value = "Submit"/>
<br/>
<input type = "reset" />
</form:form>
<a href="${pageContext.request.contextPath}/eco/carts/discard"> discard this order</a>
<script type="text/javascript">
$(document).ready(function(){
	$("#orderSubmit").click(function(e){
		e.preventDefault();
		$("#mainContent").load($("#order").attr("action"),$("#order").serialize());
	});
});
</script>
</body>
</html>