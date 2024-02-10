package com.example.tannersackettcustomersupport;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionRegistry {
    private static final Map<String, HttpSession> activeSessions = new HashMap<>();

    public static void addSession(String sessionId, HttpSession session) {
        activeSessions.put(sessionId, session);
    }

    public static void removeSession(String sessionId) {
        activeSessions.remove(sessionId);
    }

    public static List<Map.Entry<String, HttpSession>> getActiveSessions() {
        return new ArrayList<>(activeSessions.entrySet());
    }
}