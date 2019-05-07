

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Список книг</title>
    </head>
    <body>
        <a href="welcome">Главная страница</a>
        <h1>Список книг:</h1>
        <ul>
            <c:forEach var="book" items="${listBooks}">
                <li>
                    <a href="showBook?bookId=${book.id}">${book.nameBook} ${book.author}</a> 
                    <c:if test="${role == 'MANAGER'}">
                        <a href="deleteBook?deleteBookId=${book.id}">Удалить книгу</a>
                    </c:if>
                </li>
            </c:forEach>
        </ul>
    </body>
</html>
