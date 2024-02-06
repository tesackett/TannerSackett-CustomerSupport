<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>List of Tickets</title>
</head>
<body>
<h2>List of Tickets</h2>

<c:if test="${empty ticketDB}">
    <p>No tickets available.</p>
</c:if>

<c:if test="${not empty ticketDB}">
    <ul>
        <c:forEach var="ticketEntry" items="${ticketDB}">
            <li>
                <a href="<c:url value='/tickets' >
                        <c:param name='action' value='viewTicket' />
                        <c:param name='ticketId' value='${ticketEntry.key}' />
                    </c:url>">
                    Ticket #<c:out value="${ticketEntry.key}"/>
                </a>
            </li>
        </c:forEach>
    </ul>
</c:if>

<a href="ticketForm.jsp">Create Ticket</a>
</body>
</html>
