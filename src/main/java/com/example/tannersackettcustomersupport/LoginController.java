package com.example.tannersackettcustomersupport;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {
    private static final Map<String, String> userDB = new HashMap<>();

    static {
        userDB.put("user1", "password1");
        userDB.put("user2", "password2");

    }

    @GetMapping
    public String showLoginForm(HttpSession session, Model model) {
        if (session.getAttribute("username") != null) {
            return "redirect:/home";  // Redirect to home page
        }
        model.addAttribute("loginForm", new LoginForm());
        model.addAttribute("loginFailed", false);
        return "login";  // login.jsp view name
    }

    @PostMapping
    public String processLoginForm(@ModelAttribute("loginForm") LoginForm loginForm, HttpSession session, Model model) {
        String username = loginForm.getUsername();
        String password = loginForm.getPassword();

        if (username != null && password != null && userDB.containsKey(username) && userDB.get(username).equals(password)) {
            session.setAttribute("username", username);
            return "redirect:/home";  // Redirect to home page after successful login
        } else {
            model.addAttribute("loginFailed", true);
            return "login";  // Stay on login page with error message
        }
    }
}
