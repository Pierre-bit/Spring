<!-- ne pas mettre de body ni de header -->

<jsp:useBean id="dateFin" class="java.util.Date"/>
<c:set var="msFin" value="${dateFin.getTime()}" scope="page" />
<p>Page g�n�r�e en ${msFin - msDepart} ms</p>
