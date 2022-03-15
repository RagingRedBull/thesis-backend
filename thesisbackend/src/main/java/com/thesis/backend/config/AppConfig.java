package com.thesis.backend.config;

import lombok.Getter;
import lombok.Setter;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

@Configuration
@PropertySource("classpath:prmts.properties")
@Getter
@Setter
public class AppConfig {
    @Value("${prmts.alarming}")
    private boolean alarmingMode;
    @Value("${prmts.fire-drill-mode}")
    private boolean fireDrillMode;
    @Value("${prmts.keycloak.realm}")
    private String kcRealm;
    @Value("${prmts.keycloak.url}")
    private String kcUrl;
    @Value("${prmts.keycloak.client-id}")
    private String kcClientId;
    @Value("${prmts.keycloak.secret}")
    private String kcSecret;
    @Value("${prmts.keycloak.grant-type}")
    private String kcGrantType;
    @Value("${prmts.twilio.sid}")
    private String twilioSid;
    @Value("${prmts.twilio.auth-token}")
    private String twilioAuthToken;
    @Value("${prmts.twilio.number}")
    private String twilioNumber;
    @Value("${prmts.logo}")
    private String logoPath;

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Keycloak getKeycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(kcUrl)
                .realm(kcRealm)
                .clientId(kcClientId)
                .clientSecret(kcSecret)
                .grantType(kcGrantType)
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(20).build())
                .build();
    }
}
