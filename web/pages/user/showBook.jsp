<%-- 
    Document   : showBook
    Created on : May 7, 2019, 9:25:24 AM
    Author     : pupil
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Книга</title>
    </head>
    <body>
        <link rel="stylesheet" href="css/"
        <h1>Описание выбранной книги</h1>
        Обложка книги:<br>
        <img class="coverImg" src="insertFile/${bookCover.cover.name}"><br>
        Id: ${book.id}<br>
        Название: ${book.nameBook}<br>
        Автор: ${book.author}<br>
        Год издания: ${book.yearPublished}<br>
        ISBN: ${book.isbn}<br>
        Количество экземпляров: ${book.count}<br>
    </body>
</html>