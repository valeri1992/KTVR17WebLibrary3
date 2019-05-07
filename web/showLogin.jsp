
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/showLogin.css">
<div class="colorBack">
        <h1>Введите логин и пароль</h1>
        ${info}<br>
        <form action="login" method="POST" onsubmit="return validate()" name="form1" id="_form1">
             Логин:<br>
            <input type="text" name="login" id="login"><br>
            <br>
             Пароль:<br>
            <input type="password" name="password" id="password"><br>
            <br>
            <input type="submit" value="Войти" id="button">
        </form><br><br>
        <a href="newUser">Зарегистрироваться</a>
        <script src="${pageContext.request.contextPath}/js/showLogin.js"></script>
        <a href="welcome">Главная страница</a>
</div>   
