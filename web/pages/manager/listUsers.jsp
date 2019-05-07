
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/welcome.css">
        <title>Список читателей</title>
    </head>
    <body>
        <a href="welcome">Главная страница</a>
        <h1>Список читателей</h1>
        <ul>
            <c:forEach var="user" items="${listUsers}">
                <li>${user.name} ${user.surname}</li>
            </c:forEach>
        </ul>
    </body>
</html>
