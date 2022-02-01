<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.util.JspHelper" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
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
                       pattern="[А-Яа-яa-zA-Z0-9\s]{1,}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <c:choose>
                    <c:when test="${type eq ContactType.MAIL}">
                        <dd><input type="email" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
                    </c:when>
                    <c:otherwise>
                        <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
                    </c:otherwise>
                </c:choose>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="type" items="${SectionType.values()}">
            <c:choose>
                <c:when test="${type eq SectionType.EXPERIENCE || type eq SectionType.EDUCATION}">
                    <h3>${type.getTitle()}</h3>
                    <c:set var="organizationCount" value="${0}"/>
                    <c:forEach var="organization" items="${resume.getSection(type).organizations}">
                        <h3><label>Название организации
                            <input type="text" name="${type.name()}organizationName" size="50"
                                   value="${organization.homePage.getName()}">
                        </label></h3>
                        <p><label>Сайт организации
                            <input type="url" name="${type.name()}url" size="30"
                                   value="${organization.homePage.getUrl()}"
                                   placeholder="Введите ссылку на сайт, если он есть">
                        </label></p>
                        <c:forEach var="position" items="${organization.positions}">

                            <p><label>Дата начала работы
                                <input type="text" name="${type.name()}${organizationCount}startDate"
                                       value="${DateUtil.format(position.getStartDate())}" placeholder="MM/yyyy"
                                       size="7">
                            </label></p>
                            <p><label>Дата окончания работы
                                <input type="text" name="${type.name()}${organizationCount}endDate"
                                       value="${DateUtil.format(position.getEndDate())}"
                                       placeholder="MM/yyyy или Сейчас" size="15">
                            </label></p>
                            <p><label>Должность
                                <input type="text" name="${type.name()}${organizationCount}title" size="40"
                                       value="${position.title}">
                            </label></p>
                            <c:if test="${type eq SectionType.EXPERIENCE }">
                                <p><label>Описание функциональных обязанностей
                                    <input type="text" name="${type.name()}${organizationCount}description" size="120"
                                           value="${position.description}">
                                </label></p>
                            </c:if>
                        </c:forEach>
                        <h4>Добавить новую позицию для организации ${organization.homePage}</h4>
                        <p><label>Укажите дату начала работы
                            <input type="text" name="${type.name()}${organizationCount}startDate" placeholder="MM/yyyy"
                                   size="7">
                        </label></p>
                        <p><label>Укажите дату окончания работы
                            <input type="text" name="${type.name()}${organizationCount}endDate"
                                   placeholder="MM/yyyy или Сейчас" size="15">
                        </label></p>
                        <p><label>Укажите должность
                            <input type="text" name="${type.name()}${organizationCount}title" size="40">
                        </label></p>
                        <c:if test="${type eq SectionType.EXPERIENCE }">
                            <p><label>Опишите функциональные обязанности
                                <input type="text" name="${type.name()}${organizationCount}description" size="120"
                                       value="${position.description}">
                            </label></p>
                        </c:if>
                        <c:set var="organizationCount" value="${organizationCount+1}"/>
                    </c:forEach>
                    <h3>Добавить новую организацию</h3>
                    <p><label>Укажите название организации
                        <input type="text" name="${type.name()}organizationName" size="50">
                    </label></p>
                    <p><label>Укажите сайт организации
                        <input type="url" name="${type.name()}url" size="30"
                               placeholder="Введите ссылку на сайт, если он есть">
                    </label></p>
                    <p><label>Укажите дату начала работы
                        <input type="text" name="${type.name()}${organizationCount}startDate" placeholder="MM/yyyy"
                               size="7">
                    </label></p>
                    <p><label>Укажите дату окончания работы
                        <input type="text" name="${type.name()}${organizationCount}endDate"
                               placeholder="MM/yyyy или Сейчас" size="15">
                    </label></p>
                    <p><label>Укажите должность
                        <input type="text" name="${type.name()}${organizationCount}title" size="40">
                    </label></p>
                    <c:if test="${type eq SectionType.EXPERIENCE }">
                        <p><label>Опишите функциональные обязанности
                            <input type="text" name="${type.name()}${organizationCount}description" size="120">
                        </label></p>
                    </c:if>
                    <input type="hidden" name="${type.name()}" value="${0}">
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