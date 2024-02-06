
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ticket Form</title>
</head>
<body>
<h2>Ticket Form</h2>
<form action="tickets" method="POST" enctype="multipart/form-data">

    <label for="customerName">Customer Name:</label>
    <input type="text" id="customerName" name="customerName">
    <br>
    <label for="subject">Subject:</label>
    <input type="text" id="subject" name="subject">
    <br>
    <label for="body">Body:</label>
    <textarea id="body" name="body"></textarea>
    <br>
    <label for="attachment">Attachment:</label>
    <input type="file" id="attachment" name="attachment">
    <br>
    <input type="hidden" name="action" value="createTicket">
    <input type="submit" value="Submit">
</form>
</body>
</html>
