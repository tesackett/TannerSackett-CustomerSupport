package com.example.tannersackettcustomersupport;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    private final Map<Integer, Ticket> ticketMap = new HashMap<>();

    public TicketController() {
        // Initialize your ticket map or load tickets if needed
    }

    @GetMapping
    public String listTickets(Model model) {
        model.addAttribute("ticketDB", ticketMap);
        return "listTickets";  // Corresponding JSP or Thymeleaf view
    }

    @GetMapping("/view")
    public String viewTicket(@RequestParam("ticketId") int ticketId, Model model, HttpServletResponse response) throws IOException {
        Ticket ticket = ticketMap.get(ticketId);
        if (ticket == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ticket not found");
            return null;
        }
        model.addAttribute("ticket", ticket);
        return "viewTicket";  // View for displaying ticket details
    }

    @GetMapping("/new")
    public String showTicketForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        return "ticketForm"; // Form view
    }

    @PostMapping
    public String createTicket(@ModelAttribute("ticket") Ticket ticket) {
        // Add ticket to your ticketMap or database
        return "redirect:/tickets";
    }

    @GetMapping("/download")
    public void downloadAttachment(@RequestParam("ticketId") int ticketId, @RequestParam("attachmentId") int attachmentId, HttpServletResponse response) throws IOException {
        Ticket ticket = ticketMap.get(ticketId);
        if (ticket == null || !ticket.getAttachments().containsKey(attachmentId)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Attachment not found");
            return;
        }

        Attachment attachment = ticket.getAttachments().get(attachmentId);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + attachment.getName());
        try (OutputStream outStream = response.getOutputStream()) {
            outStream.write(attachment.getContents());
        }
    }
}
