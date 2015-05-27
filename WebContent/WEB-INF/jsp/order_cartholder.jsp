<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"%>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
<div id="cart">
 <jsp:include page="cart_partial.jsp"></jsp:include>
</div>
<div id="orderview">
<c:choose>
<c:when test="${morder.state=='processing'}">
	<c:url var="morderurl" value="/eco/orders/complete" />
</c:when>
<c:otherwise>
	<c:url var="morderurl" value="" />
</c:otherwise>
</c:choose>
 <form:form action="${morderurl}" method = "post" commandName="morder" >
	<label>address 1
	<form:input type = "text" path="address1" /> <form:errors path="address1"/></label>
	<br/>
	<label>address 2
	<form:input type = "text" path="address2" /> <form:errors path="address2"/></label>
	<br/>
	<label>city
	<form:input type = "text" path="city" /> <form:errors path="city"/></label>
	<br/>
	<c:if test="${morder.state=='processing'}">
		<input id="morderSubmit" type = "submit" value = "Submit"/>
	</c:if>
</form:form>
</div>
</body>
<script type="text/javascript">
$(document).ready(function(){
	$("#morderSubmit").click(function(e){
		e.preventDefault();
		$("#cartholder").load($("#morder").attr("action"),$("#morder").serialize());
	});
})
</script>
</html>