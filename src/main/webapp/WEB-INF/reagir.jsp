<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Réagir à un Gif</title>
<link
	href="/css/${sessionScope.utilisateur.theme.nom.toLowerCase()}.css"
	rel="stylesheet">
</head>
<body>
	<h1>Réagir au gif de ${gif.utilisateur.prenom}</h1>
	<c:if test="${gif.getClass().simpleName eq 'GifDistant'}">
		<img src="${gif.url}" height="200">
	</c:if>
	<c:if test="${gif.getClass().simpleName eq 'GifTeleverse'}">
		<img src="/images/${gif.id}.gif" height="200">
	</c:if>
	<form:form modelAttribute="reaction" action="reagir" method="post">
	<form:hidden path="gif.id" />
	<form:select path="emotion">
		<form:option value="0">Merci de choisir une émotion</form:option>
		<form:options items="${emotions}" itemValue="id" itemLabel="nom"></form:options>

	</form:select>
	<form:errors path="emotion" cssClass="erreur"></form:errors>
	<form:button>Réagir</form:button>
	</form:form>
</body>
</html>