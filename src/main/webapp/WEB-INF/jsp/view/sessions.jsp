<%@ page import="java.util.List" %>
<%@ page import="com.example.tannersackettcustomersupport.SessionListenerServlet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="now" value="<%=System.currentTimeMillis()%>" />
<html>
<head>
    <title>Session Management</title>
</head>
<body>
<a href="<c:url value='/login'><c:param name='logout'/></c:url>">Logout</a>
<h2>Active Sessions</h2>
There are a total of <c:out value="${numSessions}"/> active sessions.
<ul>
    <c:forEach items="${sessionList}" var="session">
        <li><c:out value="${session.id} - ${session.username} - last active ${(now-session.lastAccessed)/1000} seconds ago"/></li>
    </c:forEach>
</ul>
</body>
</html>
