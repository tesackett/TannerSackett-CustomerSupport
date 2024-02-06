<html>
<head>
    <title>View Ticket</title>
</head>
<body>
<h2>View Ticket</h2>
<p>Customer Name: ${ticket.customerName}</p>
<p>Subject: ${ticket.subject}</p>
<p>Body: ${ticket.body}</p>

<p>Attachments:</p>
<c:if test="${not empty ticket.attachments}">
    <ul>
        <c:forEach var="attachment" items="${ticket.attachments}">
            <li><a href='tickets?action=downloadAttachment&ticketId=${ticketId}&attachmentId=${attachment.id}'>${attachment.name}</a></li>
        </c:forEach>
    </ul>
</c:if>

<a href='tickets'>Back to Tickets</a>
</body>
</html>
