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
<table border = "1">
<thead>
<tr>
<th>Title</th><th>Description</th><th>Price</th><th>icon</th><th>Action</th>
</tr>
</thead>
<tbody>

<c:forEach var="product" items= "${products}"
	varStatus="productCount">
            <tr>
            <td>${product.title}</td>
               <td>${product.description}</td>
               <td>${product.price}</td>
                <td>
                <img src="${product.imageUrl}" alt="image not available">
                </td>
                <td>
                <a href="${pageContext.request.contextPath}/eco/carts/add/${product.productId}" class="add">add</a>
                <a href="${pageContext.request.contextPath}/eco/carts/remove/${product.productId}" class="remove" productId="${product.productId}">remove</a>
                </td>
           </tr>

</c:forEach>

 </tbody>
</table>

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
		$("#cart").load($(this).attr("href"));
	});
	$("#checkout").click(function(e){
		e.preventDefault();
		$("#mainContent").load($(this).attr("href"));
	});
   })
</script>
</body>
</html>