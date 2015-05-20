<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"%>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style_new.css"  media="screen" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style_response.css"  media="screen" />
<script src="${pageContext.request.contextPath}/resources/js/jquery.min-214.js"></script>
<title>Order details</title>
</head>
<body>
<div id="wrap">

<div id="header"></div>


<div id="catalogue">
<table border = "1">
<thead>
<tr>
<th>Title</th><th>Description</th><th>Price</th><th>icon</th><th>Action</th>
</tr>
</thead>
<tbody>

<c:forEach var="oitem" items= "${cart.items}" varStatus="oitemCount">
            <tr>
            <td>${oitem.product.title}</td>
               <td>${oitem.product.description}</td>
               <td>${oitem.product.price}</td>
                <td>
                <img src="${oitem.product.imageUrl}" alt="image not available">
                </td>
                <td>
                <c:if test="${morder.state=='processing'}">
	                <a href="${pageContext.request.contextPath}/eco/orders/addItem/${oitem.product.productId}" class="add">add</a>
	                <a href="${pageContext.request.contextPath}/eco/orders/removeItem/${oitem.product.productId}" class="remove" productId="${oitem.product.productId}">remove</a>
                </c:if>
                </td>
           </tr>

</c:forEach>

 </tbody>
</table>
<a href="${pageContext.request.contextPath}/eco/mainPage"> Back to home</a>
</div>
<div id="cartholder"> 
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
		<input type = "submit" value = "Submit"/>
	</c:if>
</form:form>
</div>
</div>
</div>

<script type="text/javascript">
   $(document).ready(function(){
	$(".add").click(function(e){
		e.preventDefault(); 
		$("#cart").load($(this).attr("href"));
	});
	$(".remove").click(function(e){
		if($(".proId").length==0){return false;}
		e.preventDefault();
		var pid=$(this).attr("productId");var found=false;
		$(".proId").each(function(i,e){
			if(pid==e.value){found=true;}
		});
		if(!found){return false;}
		if ($(".proId").length==1){
			if ($(".proId").prev().children()[1].innerHTML*1<=1){
				alert("only one item left");return false;}
			}
		$("#cart").load($(this).attr("href"));
	});
   })
</script>
</body>
</html>