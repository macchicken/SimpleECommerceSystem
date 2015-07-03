<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"%>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Search products</title>
</head>
<body>
<div id="wrap">

<div id="header"></div>

<div id="catalogue">
	<jsp:include page="productCatalogue.jsp"></jsp:include>
</div>
<div id="cartholder"> 
<div id="cart">
 <jsp:include page="cart_partial.jsp"></jsp:include>
</div>
<div id="cartfoot">
 <input type="button" class="btn btn-info btn-sm" href="${pageContext.request.contextPath}/eco/carts/checkout" name="checkout" id="checkout" value="Checkout">
</div>
</div>
</div>

<script type="text/javascript">
   $(document).ready(function(){
	$("#checkout").click(function(e){
		e.preventDefault();
		if($(".proId").length==0){alert("please choose at least one item before checkout");return false;}
		$("#cartholder").load($(this).attr("href"));
	});
   })
</script>
</body>
</html>