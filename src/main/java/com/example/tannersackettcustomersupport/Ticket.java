package com.example.tannersackettcustomersupport;
import java.util.HashMap;
import java.util.Map;

public class Ticket {
    private String customerName;
    private String subject;
    private String body;
    private Map<Integer, Attachment> attachments;

    public Ticket() {
        attachments = new HashMap<>();
    }

    public Ticket(String customerName, String subject, String body, Map<Integer, Attachment> attachments) {
        this.customerName = customerName;
        this.subject = subject;
        this.body = body;
        this.attachments = attachments;
    }

    // Getters and setters
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<Integer, Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Map<Integer, Attachment> attachments) {
        this.attachments = attachments;
    }

    // Helper methods
    public void addAttachment(Integer index, Attachment attachment) {
        attachments.put(index, attachment);
    }

    public int getNumberOfAttachments() {
        return attachments.size();
    }

    public Attachment getAttachment(Integer index) {
        return attachments.get(index);
    }

    public Map<Integer, Attachment> getAllAttachments() {
        return attachments;
    }
}