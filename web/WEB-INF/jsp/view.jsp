<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.urise.webapp.util.JspHelper" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>Резюме ${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <h2>Контакты&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
    <p>${contactEntry.getKey().toHtml(contactEntry.getValue())}</p>
    </c:forEach>
    <p>
        <c:forEach var="sectionEntry" items="${resume.sections}">
    <h2>${sectionEntry.getKey().getTitle()} <a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2></h2>
    <p>${JspHelper.toJspMap(sectionEntry, false)}</p>
    </c:forEach>
    </p>
    <button type="reset" onclick="window.history.go(-1)">Ок</button>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
