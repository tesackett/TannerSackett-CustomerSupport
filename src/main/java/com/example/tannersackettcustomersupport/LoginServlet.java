package com.example.tannersackettcustomersupport;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name="LoginServlet", value="/login")
public class LoginServlet extends HttpServlet {
    public static final Map<String, String> userDB = new HashMap<>();
    static {
        userDB.put("user1", "password1");
        userDB.put("user2", "password2");
        // Add more users as needed
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        // Check if already logged in
        if (session.getAttribute("username") != null) {
            response.sendRedirect("home"); // Redirect to home page
        } else {
            // Forward to login page
            request.setAttribute("loginFailed", false);
            request.getRequestDispatcher("/WEB-INF/jsp/view/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Check if already logged in
        if (session.getAttribute("username") != null) {
            response.sendRedirect("home"); // Redirect to home page
            return;
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Check credentials
        if (username != null && password != null && userDB.containsKey(username) && userDB.get(username).equals(password)) {
            session.setAttribute("username", username); // Set session attribute
            response.sendRedirect("home"); // Redirect to home page after successful login
        } else {
            request.setAttribute("loginFailed", true); // Set login failed attribute
            request.getRequestDispatcher("/WEB-INF/jsp/view/login.jsp").forward(request, response); // Forward to login page
        }
    }
}