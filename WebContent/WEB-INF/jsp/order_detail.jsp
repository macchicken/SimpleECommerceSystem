<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"%>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Order details</title>
</head>
<body>

<div id="header"></div>

<div id="catalogue">
<table border = "1">
<thead>
<tr>
<th>Title</th><th>Description</th><th>Price</th><th>Icon</th>
<c:if test="${morder.state=='processing'}">
<th>Action</th>
</c:if>
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
                <c:if test="${morder.state=='processing'}">
                <td>
	                <a href="${pageContext.request.contextPath}/eco/orders/addItem/${oitem.product.productId}" class="add">add</a>
	                <a href="${pageContext.request.contextPath}/eco/orders/removeItem/${oitem.product.productId}" class="remove" productId="${oitem.product.productId}">remove</a>
                </td>
                </c:if>
           </tr>

</c:forEach>

 </tbody>
</table>
</div>
<div id="cartholder"> 
 <jsp:include page="order_cartholder.jsp"></jsp:include>
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
		if ($(".proId").length==1){
			if ($(".proId").prev().children()[1].innerHTML*1<=1){
				alert("only one item left");return false;}
		}
		var pid=$(this).attr("productId");var found=false;
		$(".proId").each(function(i,e){
			if(pid==e.value){found=true;}
		});
		if(!found){return false;}
		$("#cart").load($(this).attr("href"));
	});
   })
</script>
</body>
</html>