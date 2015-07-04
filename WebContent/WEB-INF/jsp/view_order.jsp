<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"%>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Orders</title>
</head>
<body>

<div id="header"></div>

<div id="catalogue">
<sec:authorize access="hasRole('ROLE_USER')">
<c:set var='userlink' value='orders'/>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_ADMIN')">
<c:set var='userlink' value='admin'/>
</sec:authorize>
<table border = "1">
<thead>
<tr>
<th>Id</th><th>Address</th><th>Shipping Cost</th><th>Total Cost</th><th>State</th><th>Order Time</th><th>Action</th>
<sec:authorize access="hasRole('ROLE_ADMIN')">
<th>User</th>
</sec:authorize>
</tr>
</thead>
<tbody>

<c:forEach var="order" items="${orders.elementList}" varStatus="orderCount">
            <tr>
	           <td>${order.id}</td>
               <td>${order.address1},${order.address2},${order.city}</td>
               <td>${order.shippingCost}</td>
               <td>${order.totalCost}</td>
               <td>${order.state}</td>
               <td>${order.createdTime}</td>
               <sec:authorize access="hasRole('ROLE_USER')">
                	<td><a href="orders/detail/${order.id}?pageIndex=${order.pageIndex}" class="detail">detail</a></td>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_ADMIN')">
						<c:choose>
							<c:when test="${order.state=='processing'}">
								<td><a href="admin/update/${order.id}?pageIndex=${order.pageIndex}" class="update">update</a></td>
	    					</c:when>
    						<c:otherwise><td></td></c:otherwise>
						</c:choose>
						<td>${order.user}</td>
				</sec:authorize>
           </tr>
</c:forEach>

</tbody>
</table>
<br/>
<div id="pageArea">
	<c:forEach var="i" begin="1" end="${orders.totalPage}">
		<a href="${userlink}?pageNumber=${i}" value="${i}">${i}</a>&nbsp
	</c:forEach>
	<input id="currentPage" type="text" hidden value="${orders.currentPage}">
</div>
</div>

</body>
<script type="text/javascript">
$(document).ready(function(){
	$(".update,.detail").click(function(e){
		e.preventDefault(); 
		$("#mainContent").load($(this).attr("href"));
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
				$("#mainContent").load(urllink);
		});}
	});
});
</script>
</html>