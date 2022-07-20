<% /* JSP: Java Server Page (1999) Une JSP débute par les directives

La vue ne fait que de l'affichage


*/ %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% /* On fait appel à une bibliothèque de balises (taglib) : JSTL
    Java Standard Tag Library
    */ %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Calendrier Gif</title>
<link href="style/theme1.css" rel="stylesheet">
</head>
<body>
<h1>Calendrier Gif : <c:if test="${emotion eq null}">Ajouter</c:if><c:if test="${emotion ne null}">Modifier</c:if> une émotion</h1>
<form action="emotion" method="post">
<label>Nom</label><input type="text" name="NOM" value="${emotion.nom}"><br>
<label>Code</label><input type="text" name="CODE" value="${fn:escapeXml(emotion.code)}"><br>
<input type="hidden" name="ID" value="${emotion.id}">
<c:if test="${emotion eq null}">
	<input type="submit" value="Ajouter">
</c:if>
<c:if test="${emotion ne null}">
	<input type="submit" value="Modifier">
</c:if>
</form>
</body>
</html>