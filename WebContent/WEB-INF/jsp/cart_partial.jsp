<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>shopping cart table</title>
</head>
<body>
	<h2>Items in cart :</h2>
	<table>

		<c:forEach var="cartItem" items="${cart.items}">
			<c:if test="${cartItem.quantity>0}">
				<tr>
					<td>${cartItem.product.title}</td>
					<td>${cartItem.quantity}</td>
				</tr>
				<input type="hidden" value="${cartItem.product.productId}" class="proId"/>
			</c:if>
		</c:forEach>

		<tfoot>
			<tr>
				<th>Total:</th>
				<th>${cart.total}</th>
			</tr>
		</tfoot>
	</table>
</body>
</html>