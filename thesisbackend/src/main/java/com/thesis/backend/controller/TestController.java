package com.thesis.backend.controller;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/test")
public class TestController {

    @GetMapping(path = "/user")
    public ResponseEntity<String> currentUserInformation(Authentication authentication) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>)
                authentication.getPrincipal();

        return new ResponseEntity<>(principal.getKeycloakSecurityContext().getToken().getRealmAccess()
                .getRoles().toString(), HttpStatus.OK);
    }

    @GetMapping(path = "/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return new ResponseEntity<>("logout", HttpStatus.OK);
    }
}
