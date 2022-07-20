<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- <%@include file="piedDePage.jsp"%> --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Calendrier Gif</title>
<link href="style/theme1.css" rel="stylesheet">
</head>
<body>
<h1>Calendrier Gif</h1>
<c:if test="${param.notification ne null}"><h2>${param.notification}</h2></c:if>
<form action="connexion" method="post">
    <input type="email" name="EMAIL" placeHolder="Email" required><br>
    <input type="password" name="MOT_DE_PASSE" placeHolder="Mot de Passe" required><br>
    <input type="submit" value="Connexion">
</form>
<a href="inscription">S'inscrire</a>
<p>
<a href="/h2-console">H2</a>
<%@include file="piedDePage.jsp"%>
</body>
</html>