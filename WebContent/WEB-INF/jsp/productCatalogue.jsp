<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"%>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:choose>
	<c:when test="${searchFlag=='prod'}">
		<c:set var='urllink' value='products/search'/>
	</c:when>
	<c:otherwise><c:set var='urllink' value='products/search/cart'/></c:otherwise>
</c:choose>
<table border = "1">
<thead>
<tr>
<th>Title</th><th>Description</th><th>Price</th><th>Icon</th><th>Action</th>
</tr>
</thead>
<tbody>
<c:forEach var="product" items= "${products.elementList}" varStatus="productCount">
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
<br/>
<div id="pageArea">
	<c:forEach var="i" begin="1" end="${products.totalPage}">
		<a href="${urllink}?pageNumber=${i}&continued=true" value="${i}">${i}</a>&nbsp
	</c:forEach>
	<input id="currentPage" type="text" hidden value="${products.currentPage}">
</div>
</body>
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
	var cPag=$("#pageArea").find("#currentPage").val()*1;
	console.log(cPag);
	$("#pageArea").find("a").each(function(i,e){
		if (($(e).attr("value")*1)===cPag){
			$(e).css("text-decoration","none");
			$(e).click(function(ee){
				ee.preventDefault();
				return false;
			});
		}else{
			$(e).click(function(ee){
				ee.preventDefault();
				var urllink=$(this).attr('href');
				$("#catalogue").load(urllink);
		});}
	});
});
</script>
</html>