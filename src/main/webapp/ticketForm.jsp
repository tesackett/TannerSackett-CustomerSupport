<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create Ticket</title>
</head>
<body>
<h2>Create Ticket</h2>
<form action="tickets" method="post" enctype="multipart/form-data">
    <input type="hidden" name="action" value="createTicket">
    <label for="customerName">Customer Name:</label><br>
    <input type="text" id="customerName" name="customerName" required><br>
    <label for="subject">Subject:</label><br>
    <input type="text" id="subject" name="subject" required><br>
    <label for="body">Body:</label><br>
    <textarea id="body" name="body" rows="4" required></textarea><br>
    <label for="attachment">Attachment:</label><br>
    <input type="file" id="attachment" name="attachment"><br>
    <input type="submit" value="Submit">
</form>
</body>
</html>
