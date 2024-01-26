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