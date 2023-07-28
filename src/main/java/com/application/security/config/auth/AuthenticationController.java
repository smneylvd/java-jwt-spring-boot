package com.application.security.config.auth;

import com.application.security.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/login")
    public RedirectView home(Model model, HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("email");
        String password = request.getParameter("password");
        System.out.println(username + " AND " + password);
        try {
            AuthenticationRequest req = new AuthenticationRequest(username, password);
            AuthenticationResponse res = authenticate(req).getBody();
            assert res != null;
            String token = res.getToken();
            response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

            System.out.println("REQ: " + response.getHeader(HttpHeaders.AUTHORIZATION));

            return new RedirectView("/home");

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            // Authentication failed, handle the failure (e.g., show error message)
            model.addAttribute("error", "Invalid credentials");

            // Return to the login page
            return new RedirectView("/login");
        }
    }

    @GetMapping("/user")
    public ResponseEntity<User> user (Model model, HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(service.getUser(request));
    }


}
