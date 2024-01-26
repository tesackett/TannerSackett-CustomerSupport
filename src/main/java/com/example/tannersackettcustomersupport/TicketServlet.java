package com.example.tannersackettcustomersupport;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

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
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h2>List of Tickets</h2>");

        if (ticketMap.isEmpty()) {
            out.println("<p>No tickets available.</p>");
        } else {
            out.println("<ul>");
            for (Map.Entry<Integer, Ticket> entry : ticketMap.entrySet()) {
                int ticketId = entry.getKey();
                out.println("<li><a href='tickets?action=viewTicket&ticketId=" + ticketId + "'>Ticket #" + ticketId + "</a></li>");
            }
            out.println("</ul>");
        }

        out.println("<a href='ticketForm.jsp'>Create Ticket</a>");
        out.println("</body></html>");
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
                // If attachment processed successfully, add it to the ticket
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