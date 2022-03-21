package com.thesis.backend.service;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    public AccessToken getKeycloakPrincipal(Authentication authentication) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>)
                authentication.getPrincipal();
        return principal.getKeycloakSecurityContext().getToken();
    }
}
