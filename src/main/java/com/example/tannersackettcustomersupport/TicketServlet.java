package com.example.tannersackettcustomersupport;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "TicketServlet", value = "/tickets")
@MultipartConfig(fileSizeThreshold = 5242880, maxFileSize = 20971520L, maxRequestSize = 4194340L)
public class TicketServlet extends HttpServlet {
    private Map<Integer, Ticket> ticketMap;

    public void init() {
        ticketMap = new HashMap<>();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "listTickets";
        }

        switch (action) {
            case "listTickets":
                listTickets(request, response);
                break;
            case "viewTicket":
                viewTicket(request, response);
                break;
            case "showTicketForm":  // Add this case
                showTicketForm(request, response);
                break;
            case "downloadAttachment":
                downloadAttachment(request, response);
                break;
            default:
                listTickets(request, response);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("createTicket".equals(action)) {
            createTicket(request, response);
        } else {
            listTickets(request, response);
        }
    }
    private void listTickets(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("ticketDB", ticketMap);


        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/view/listTickets.jsp");
        dispatcher.forward(request, response);
    }


    private void viewTicket(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int ticketId = Integer.parseInt(request.getParameter("ticketId"));
        Ticket ticket = ticketMap.get(ticketId);

        if (ticket == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ticket not found");
            return;
        }

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h2>View Ticket</h2>");
        out.println("<p>Customer Name: " + ticket.getCustomerName() + "</p>");
        out.println("<p>Subject: " + ticket.getSubject() + "</p>");
        out.println("<p>Body: " + ticket.getBody() + "</p>");
        out.println("<p>Attachments:</p>");

        if (ticket.getAttachments().isEmpty()) {
            out.println("<p>No attachments available.</p>");
        } else {
            for (Map.Entry<Integer, Attachment> entry : ticket.getAttachments().entrySet()) {
                Attachment attachment = entry.getValue();
                out.println("<p><a href='tickets?action=downloadAttachment&ticketId=" + ticketId + "&attachmentId=" + entry.getKey() + "'>" + attachment.getName() + "</a></p>");
            }
        }

        out.println("<a href='tickets'>Back to Tickets</a>");
        out.println("</body></html>");
    }

    private void createTicket(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Extract data from the request
        String customerName = request.getParameter("customerName");
        String subject = request.getParameter("subject");
        String body = request.getParameter("body");

        // Create a new Ticket object
        Ticket ticket = new Ticket();
        ticket.setCustomerName(customerName);
        ticket.setSubject(subject);
        ticket.setBody(body);

        Part attachmentPart = request.getPart("attachment");
        if (attachmentPart != null) {
            Attachment attachment = processAttachment(attachmentPart);
            if (attachment != null) {
                // If attachment successfull, add it to the ticket
                ticket.addAttachment(attachment.getId(), attachment);
            }
        }
        int ticketId;
        synchronized (this) {
            ticketId = ticketMap.size() + 1;
            ticketMap.put(ticketId, ticket);
        }

        // Redirect to view the newly created ticket
        response.sendRedirect("tickets?action=viewTicket&ticketId=" + ticketId);
    }
    private Attachment processAttachment(Part file) throws IOException {
        InputStream in = file.getInputStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();


        int read;
        final byte[] bytes = new byte[1024];
        while ((read = in.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }

        // Creating an Attachment object
        Attachment attachment = new Attachment();
        attachment.setName(file.getSubmittedFileName());
        attachment.setContents(out.toByteArray());

        return attachment;
    }
    private void showTicketForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward the request to ticketForm.jsp
        request.getRequestDispatcher("/ticketForm.jsp").forward(request, response);
    }

    private void downloadAttachment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int ticketId = Integer.parseInt(request.getParameter("ticketId"));
        int attachmentId = Integer.parseInt(request.getParameter("attachmentId"));

        Ticket ticket = ticketMap.get(ticketId);

        if (ticket == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ticket not found");
            return;
        }

        Attachment attachment = ticket.getAttachment(attachmentId);

        if (attachment == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Attachment not found");
            return;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + attachment.getName());

        try (OutputStream outStream = response.getOutputStream()) {
            outStream.write(attachment.getContents());
        }
    }
}