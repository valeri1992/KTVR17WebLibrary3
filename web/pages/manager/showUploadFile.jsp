<%-- 
    Document   : newjspshowUploadFile
    Created on : May 7, 2019, 11:06:23 AM
    Author     : pupil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Загрузка файла</title>
    </head>
    <body>
        <h1>Загрузка файла!</h1>
        <form action="uploadFile" method="POST" enctype="multipart/form-data">
            <input type="text" name="description"><br>
            <input type="file" name="file"><br>
            <input type="submit" value="Загрузить">
        </form>
        
    </body>
</html>
