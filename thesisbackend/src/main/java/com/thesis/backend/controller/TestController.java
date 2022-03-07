package com.thesis.backend.controller;

import com.thesis.backend.config.AppConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(path = "/test")
@RequiredArgsConstructor
public class TestController {
    private final Environment environment;
    private final AppConfig appConfig;

    @GetMapping(path = "/user")
    public ResponseEntity<String> currentUserInformation(Authentication authentication) {
        log.info("Getting User");
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>)
                authentication.getPrincipal();
        AccessToken token = principal.getKeycloakSecurityContext().getToken();
        return new ResponseEntity<>(token.getRealmAccess().getRoles().toString()
                + token.getName(), HttpStatus.OK);
    }

    @GetMapping(path = "/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return new ResponseEntity<>("logout", HttpStatus.OK);
    }

    @GetMapping(path = "/alarming")
    public ResponseEntity<Object> checkAlarmingMode() {
        return ResponseEntity.ok(appConfig.isEnabledAlarmingMode());
    }

    @GetMapping(path = "/alarming/update")
    public ResponseEntity<Object> updateAlarmingMode(@RequestParam boolean update) {
        appConfig.setEnabledAlarmingMode(update);
        return ResponseEntity.ok(null);
    }

    ;
}
