package com.example.tannersackettcustomersupport;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";  // assuming you have an index.jsp in /WEB-INF/jsp/
    }
}