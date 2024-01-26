package com.example.tannersackettcustomersupport;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

public class TicketTest {

    @Test
    public void testTicketMethods() {
        Ticket ticket = new Ticket();
        ticket.setCustomerName("Tanner Sackett");
        ticket.setSubject("Issue with login");
        ticket.setBody("I am unable to login to the system.");

        Attachment attachment1 = new Attachment();
        attachment1.setName("screenshot.png");
        attachment1.setContents(new byte[1024]);

        Attachment attachment2 = new Attachment();
        attachment2.setName("log.txt");
        attachment2.setContents(new byte[512]);

        Map<Integer, Attachment> attachments = new HashMap<>();
        attachments.put(1, attachment1);
        attachments.put(2, attachment2);

        ticket.setAttachments(attachments);

        assertEquals("Tanner Sackett", ticket.getCustomerName());
        assertEquals("Issue with login", ticket.getSubject());
        assertEquals("I am unable to login to the system.", ticket.getBody());
        assertEquals(2, ticket.getNumberOfAttachments());
        assertEquals(attachment1, ticket.getAttachment(1));
        assertEquals(attachment2, ticket.getAttachment(2));
        assertEquals(2, ticket.getAllAttachments().size());
    }
}
