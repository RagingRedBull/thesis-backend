package com.thesis.backend.controller;

import com.thesis.backend.config.AppConfig;
import com.thesis.backend.model.dto.logs.PostFireReportCompartmentDto;
import com.thesis.backend.repository.SensorLogRepository;
import com.thesis.backend.service.PostFireReportService;
import com.thesis.backend.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/test")
@RequiredArgsConstructor
public class TestController {
    private final Environment environment;
    private final SensorLogRepository sensorLogRepository;
    private final ReportService reportService;
    private final AppConfig appConfig;
    private final Keycloak keycloak;
    private final PostFireReportService postFireReportService;
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
        return ResponseEntity.ok(appConfig.isAlarmingMode());
    }

    @GetMapping(path = "/alarming/update")
    public ResponseEntity<Object> updateAlarmingMode(@RequestParam boolean update) {
        appConfig.setAlarmingMode(update);
        return ResponseEntity.ok(null);
    }

    @GetMapping(path = "/all-users")
    public ResponseEntity<Object> getUsers() {
        List<UserRepresentation> users = keycloak.realm("prmts").users().list();
        List<String> attributes = new ArrayList<>();
        for (UserRepresentation user : users) {
            if (user.getAttributes() != null) {
                log.info("Email: " + user.getEmail());
                log.info("Cellphone: " + user.getAttributes().get("cellphone").get(0));
            }
        }
        reportService.sendSmsToUsers();
        return ResponseEntity.ok("geh");
    }

    @GetMapping("/latest-pfr")
    public ResponseEntity<Object> getLatestPFR() {
        log.info("yeet");
        Pageable page = PageRequest.of(0,10);
        Page<PostFireReportCompartmentDto> logs = sensorLogRepository.getAffectedCompartmentsByPfrId(5L, page);
        log.info(logs.toString());
        System.out.println(logs);
        return ResponseEntity.ok(logs);
    }
}
