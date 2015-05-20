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
<div id="wrap">

<div id="header"></div>


<div id="catalogue">
<table border = "1">
<thead>
<tr>
<th>Id</th><th>Address</th><th>Shipping Cost</th><th>Total Cost</th><th>State</th><th>Order Time</th><th>Action</th>
</tr>
</thead>
<tbody>

<c:forEach var="order" items= "${orders}" varStatus="orderCount">
            <tr>
	           <td>${order.id}</td>
               <td>${order.address1},${order.address2},${order.city}</td>
               <td>${order.shippingCost}</td>
               <td>${order.totalCost}</td>
               <td>${order.state}</td>
               <td>${order.createdTime}</td>
               <sec:authorize access="hasRole('USER')">
                	<td><a href="orders/detail/${order.id}">detail</a></td>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_ADMIN')">
	                	<c:if test="${order.state=='processing'}">
		                	<td><a href="admin/update/${order.id}" class="update">update</a></td>
						</c:if>
				</sec:authorize>
           </tr>

</c:forEach>

 </tbody>
</table>

</div>

</div>

</body>
<script type="text/javascript">
$(document).ready(function(){
	$(".update").click(function(e){
		e.preventDefault(); 
		$("#mainContent").load($(this).attr("href"));
	});

});
</script>
</html>