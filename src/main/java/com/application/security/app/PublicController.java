package com.application.security.app;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class PublicController {

    //    private AuthenticationManager authenticationManager;
//
//    @GetMapping
//    public String home(Model model) {
//        model.addAttribute("name", "John Doe");
//        return "index"; // Assuming "index" is the name of your Thymeleaf template
//    }
//

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("name", "John Doe");
        return "login";
    }


    @GetMapping("/home")
    public String home(Model model, HttpServletRequest request) {
        model.addAttribute("name", "John Doe");
        return "index";
    }


}
