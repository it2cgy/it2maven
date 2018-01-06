<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<h2>this is the login page!!!</h2>
<c:forEach items="${users}" var="user"> 
	${user.name}
</c:forEach>
</body>
</html>
