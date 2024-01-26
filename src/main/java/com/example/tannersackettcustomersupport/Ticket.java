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

