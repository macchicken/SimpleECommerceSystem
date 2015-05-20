<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style_new.css"  media="screen" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style_response.css"  media="screen" />
<script src="${pageContext.request.contextPath}/resources/js/jquery.min-214.js"></script>
<title>Products Pages</title>
</head>
<body>
<sec:authorize access="hasRole('ROLE_ADMIN')">
		<p><a id="oadmin" href="admin"> view all orders</a></p>
</sec:authorize>
<sec:authorize access="hasRole('USER')">
	<p>tag name to search</p>
	<input type="text"  class="input-small" id="skeyword" name="skeyword" >
	<input type="button" class="btn btn-info btn-sm" name="flickrsearch" id="flickrsearch" value="Search">
	<p><a id="vorders" href="orders"> view your order</a></p>
</sec:authorize>
<p><a href="${pageContext.request.contextPath}/logout">Log Out</a></p>
<div id="mainContent">
</div>
</body>
<script type="text/javascript">
   $(document).ready(function(){
	$("#flickrsearch").click(function(e){
		e.preventDefault();
		var kw=$("#skeyword").val();console.log(kw);
		if(/[~#^$@.。%&!*]/gi.test(kw))
		{
		    alert('special character');
		    return false;
		}
		$("#mainContent").load("products/search?keyword="+kw);
	});
	$("#vorders").click(function(e){
		e.preventDefault();
		var vohref=$(this).attr("href");
		$("#mainContent").load(vohref);
	});
	$("#oadmin").click(function(e){
		e.preventDefault();
		var oadminhref=$(this).attr("href");
		$("#mainContent").load(oadminhref);
	});
	
})
</script>
</html>
