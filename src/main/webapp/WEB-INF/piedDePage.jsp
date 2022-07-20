<!-- ne pas mettre de body ni de header -->

<jsp:useBean id="dateFin" class="java.util.Date"/>
<c:set var="msFin" value="${dateFin.getTime()}" scope="page" />
<p>Page générée en ${msFin - msDepart} ms</p>
