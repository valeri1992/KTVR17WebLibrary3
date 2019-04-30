<%-- 
    Document   : showChangePassword
    Created on : Apr 30, 2019, 10:18:39 AM
    Author     : pupil
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Новый пароль</title>
    </head>
    <body>
        <h1>Назначить новый  пароль</h1>
        <form action="changePassword" method="POST">
            <select name="userId">
                <c:forEach var="user" items="${listUsers}"
            </select>
        </form>
    </body>
</html>
