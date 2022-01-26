<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.util.JspHelper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}" required
                       pattern="[А-Яа-яa-zA-Z0-9\s]{3,}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:choose>
                <c:when test="${type eq SectionType.EXPERIENCE || type eq SectionType.EDUCATION}">
                    <h3><label>${type.getTitle()}</label></h3>
                    <textarea disabled id="${type}"
                              name="${type.name()}" cols="120"
                              rows="11">${JspHelper.toJspSection(type, resume.getSection(type))}</textarea>
                </c:when>
                <c:when test="${type eq SectionType.PERSONAL || type eq SectionType.OBJECTIVE}">
                    <h3><label>${type.getTitle()}</label></h3>
                    <textarea id="${type}"
                              name="${type.name()}" cols="120"
                              rows="3">${JspHelper.toJspSection(type, resume.getSection(type))}</textarea>
                </c:when>
                <c:otherwise>
                    <h3><label>${type.getTitle()}</label></h3>
                    <textarea id="${type}"
                              name="${type.name()}" cols="120"
                              rows="15">${JspHelper.toJspSection(type, resume.getSection(type))}</textarea>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.go(-1)">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>