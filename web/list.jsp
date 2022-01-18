<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 017 17.01.22
  Time: 10:23
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Список резюме</title>
</head>
<table align="center" cellpadding="5" border="1">
    <tr>
        <th>uuid</th>
        <th>fullName</th>
    </tr>
    <c:forEach items="${mapResumes}" var="entry">
        <tr>
            <td>${entry.key}</td>
            <td align="center">${entry.value}</td>
        </tr>
    </c:forEach>
</table>
<p align="center"><a href='<c:url value="/index.html" />'>Вернуться на главную</a></p>
</body>
</html>
