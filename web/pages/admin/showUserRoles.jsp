<%-- 
    Document   : editUserRoles
    Created on : Nov 7, 2018, 12:35:23 PM
    Author     : Melnikov
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Назначение ролей!</h1>
        <form action="changeUserRole" method="POST">
            <table>
                <tr>
                    <th>Пользователь</th>
                    <th>Новая роль</th>
                    <th></th>
                    <th></th>
                </tr>
                <tr>
                    <td>
                        <select name="user">
                            <option value="" disabled selected hidden></option>
                            <c:forEach var="entry" items="${mapUsers}">
                                <option value="${entry.key.id}">${entry.key.login}, роль: ${entry.value} </option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <select name="role">
                            <option value="" disabled selected hidden></option>
                            <c:forEach var="role" items="${listRoles}">
                                <option value="${role.id}">${role.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td><input type="submit" name="setButton" value="Назначить"></td>
                    <td><input type="submit" name="deleteButton" value="Удалить"></td>
                </tr>
            </table>
        </form>
         <a href="welcome">Главная страница</a>
    </body>
</html>
