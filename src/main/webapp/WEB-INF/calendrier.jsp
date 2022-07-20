<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<title>Calendrier</title>
<link
	href="/css/${sessionScope.utilisateur.theme.nom.toLowerCase()}.css"
	rel="stylesheet">
</head>
<body>
	<h1>Calendrier, il vous reste ${nbPointsRestant} points</h1>
	<table id="calendrier">
		<tr>
			<td>Jour</td>
			<td>Gif</td>
			<td>Utilisateur</td>
			<td>Réaction(s)</td>
		</tr>
		<c:forEach items="${pageDeJours.content}" var="jour">
			<tr>
				<td>${jour}</td>
				<c:choose>
					<c:when test="${jour.gif eq null }">
						<td colspan="3">${jour.nbPoints} points<br> <a
							href="calendrier/placerGifDistant?ID_JOUR=${jour.date}">Placer
								un Gif distant</a><br> <a
							href="calendrier/televerserGif?ID_JOUR=${jour.date}">Téléverser
								un Gif</a>
						</td>
					</c:when>
					<c:when test="${jour.gif ne null }">
						<td><c:if
								test="${jour.getGif().getLegende() ne null && jour.gif.legende ne ''}">
								<h2>${jour.gif.legende}</h2>
							</c:if> <c:if test="${jour.gif.getClass().simpleName eq 'GifDistant'}">
								<img src="${jour.gif.url}" height="200"
									alt="Super image du calendrier !">
							</c:if> <c:if test="${jour.gif.getClass().simpleName eq 'GifTeleverse'}">
								<img src="images/${jour.gif.id}.gif" height="200"
									alt="Super image du calendrier !">
							</c:if></td>
						<td>${jour.gif.utilisateur.prenom}</td>
						<td><c:forEach items="${jour.gif.reactions}" var="reaction">
								${reaction.emotion.code} ${reaction.utilisateur.prenom}<br>
							</c:forEach> <a href="calendrier/reagir?ID_GIF=${jour.gif.id}">Réagir</a></td>
					</c:when>
				</c:choose>
			</tr>
		</c:forEach>
	</table>
	<c:if test="${!pageDeJours.isFirst()}">
		<a href="calendrier?page=0">Premier</a>
		<a href="calendrier?page=${pageDeJours.number-1}">Précédent</a>
	</c:if>
	Page ${pageDeJours.getNumber()+1}
	<c:if test="${!pageDeJours.last}">
		<a href="calendrier?page=${pageDeJours.number+1}">Suivant</a>
		<a href="calendrier?page=${pageDeJours.totalPages - 1}">Dernier</a>
	</c:if>
</body>
</html>