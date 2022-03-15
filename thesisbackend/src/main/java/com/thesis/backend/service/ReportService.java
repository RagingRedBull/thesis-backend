package com.thesis.backend.service;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.thesis.backend.config.AppConfig;
import com.thesis.backend.exception.CannotBuildPdfException;
import com.thesis.backend.model.dto.SensorStatusReportLogDto;
import com.thesis.backend.model.dto.StatusReportLogDto;
import com.thesis.backend.model.entity.ContactPerson;
import com.thesis.backend.model.entity.DetectorUnit;
import com.thesis.backend.model.entity.logs.SensorStatusReportLog;
import com.thesis.backend.model.entity.logs.StatusReportLog;
import com.thesis.backend.model.enums.ReportType;
import com.thesis.backend.model.util.mapper.EntityMapper;
import com.thesis.backend.model.util.mapper.SensorStatusReportMapper;
import com.thesis.backend.model.util.mapper.StatusReportLogMapper;
import com.thesis.backend.repository.ContactPersonRepository;
import com.thesis.backend.repository.StatusReportLogRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessToken;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportService {
    private final AppConfig appConfig;
    private final StatusReportLogRepository statusReportLogRepository;
    private final SensorLogService sensorLogService;
    private final DetectorUnitService detectorUnitService;
    private final ContactPersonRepository contactPersonRepository;
    private final AuthenticationService authenticationService;

    @Async
    public void playFireWarning(String compartmentName, String floorDesc) {
        try {
            // Set property as Kevin Dictionary
            System.setProperty(
                    "freetts.voices",
                    "com.sun.speech.freetts.en.us"
                            + ".cmu_us_kal.KevinVoiceDirectory");

            // Register Engine
            Central.registerEngineCentral(
                    "com.sun.speech.freetts"
                            + ".jsapi.FreeTTSEngineCentral");

            // Create a Synthesizer
            Synthesizer synthesizer
                    = Central.createSynthesizer(
                    new SynthesizerModeDesc(Locale.US));

            // Allocate synthesizer
            synthesizer.allocate();

            // Resume Synthesizer
            synthesizer.resume();

            // Speaks the given text
            // until the queue is empty.
            for (int i = 0; i < 5; i++) {
                synthesizer.speakPlainText(
                        "WARNING! FIRE DETECTED AT " + compartmentName + ". By " + floorDesc, null);
                Thread.sleep(1000);
            }
            synthesizer.waitEngineState(
                    Synthesizer.QUEUE_EMPTY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "* 59 * * * *")
    @Transactional
    public void generateHourlyLogs() {
        EntityMapper<SensorStatusReportLog, SensorStatusReportLogDto> sensorStatusMapper = new SensorStatusReportMapper();
        List<DetectorUnit> detectorUnitList = detectorUnitService.getAll();
        List<StatusReportLog> statusReportLogList = new ArrayList<>();
        LocalDateTime dateTimeStart = LocalDateTime.of(LocalDate.now(), LocalTime.of(LocalTime.now().getHour(),
                0, 0, 0));
        LocalDateTime dateTimeEnd = LocalDateTime.of(LocalDate.now(), LocalTime.of(LocalTime.now().getHour(),
                59, 59, 999999));
        for (DetectorUnit unit : detectorUnitList) {
            StatusReportLog statusReportLog = new StatusReportLog();
            statusReportLog.setMacAddress(unit.getMacAddress());
            List<SensorStatusReportLog> sensorStatusReportLogList = unit.getAssociatedSensorSet().stream().map(
                            sensor -> sensorLogService.getSensorStatusByMacAddressBetweenStartEndDate(
                                    unit.getMacAddress(),
                                    dateTimeStart,
                                    dateTimeEnd,
                                    sensor.getName(),
                                    sensor.getType()))
                    .map(sensorStatusMapper::mapToEntity)
                    .collect(Collectors.toList());
            sensorStatusReportLogList.forEach(sensorStatusReportLog ->
                    sensorStatusReportLog.setStatusReportLog(statusReportLog));
            statusReportLog.setSensorStatusReportLogs(sensorStatusReportLogList);
            statusReportLog.setDateStart(dateTimeStart);
            statusReportLog.setDateEnd(dateTimeEnd);
            statusReportLogList.add(statusReportLog);
        }
        statusReportLogRepository.saveAll(statusReportLogList);
    }

    @Transactional
    public void generateStatusReportLog(LocalDate date) {
        EntityMapper<SensorStatusReportLog, SensorStatusReportLogDto> sensorStatusMapper = new SensorStatusReportMapper();
        List<DetectorUnit> detectorUnitList = detectorUnitService.getAll();
        List<StatusReportLog> statusReportLogList = new ArrayList<>();
        for (DetectorUnit unit : detectorUnitList) {
            for (int i = 0; i < 24; i++) {
                LocalDateTime dateTimeStart = LocalDateTime.of(date,
                        LocalTime.of(i, 0, 0, 0));
                LocalDateTime dateTimeEnd = LocalDateTime.of(date,
                        LocalTime.of(i, 59, 59, 999999999));
                StatusReportLog statusReportLog = new StatusReportLog();
                statusReportLog.setMacAddress(unit.getMacAddress());
                List<SensorStatusReportLog> sensorStatusReportLogList = unit.getAssociatedSensorSet().stream().map(
                                sensor -> sensorLogService.getSensorStatusByMacAddressBetweenStartEndDate(
                                        unit.getMacAddress(),
                                        dateTimeStart,
                                        dateTimeEnd,
                                        sensor.getName(),
                                        sensor.getType()))
                        .map(sensorStatusMapper::mapToEntity)
                        .collect(Collectors.toList());
                sensorStatusReportLogList.forEach(sensorStatusReportLog ->
                        sensorStatusReportLog.setStatusReportLog(statusReportLog));
                statusReportLog.setSensorStatusReportLogs(sensorStatusReportLogList);
                statusReportLog.setDateStart(dateTimeStart);
                statusReportLog.setDateEnd(dateTimeEnd);
                statusReportLogList.add(statusReportLog);
            }
        }
        statusReportLogRepository.saveAll(statusReportLogList);
    }

    public Page<StatusReportLogDto> generateStatusReportLog(LocalDate date, Pageable page) {
        LocalDateTime startDt = LocalDateTime.of(date, LocalTime.MIDNIGHT);
        LocalDateTime endDt = LocalDateTime.of(date, LocalTime.MAX);
        Page<StatusReportLog> statusReportLogs = statusReportLogRepository.getAllStatusReportLogsByDay(startDt, endDt, page);
        return statusReportLogs.map(this::buildSensorStatusReportLogDto);
    }

    public void sendSmsToUsers(String compartmentName, String floorDesc) {
        Twilio.init(appConfig.getTwilioSid(), appConfig.getTwilioAuthToken());
        List<ContactPerson> contactPeople = contactPersonRepository.getAllEnabled();
        for (ContactPerson person : contactPeople) {
            log.info("Email: " + person.getEmail());
            log.info("Cellphone: " + person.getPhoneNumber());
            Message message = Message.creator(
                    new PhoneNumber(person.getPhoneNumber()),
                    new PhoneNumber(appConfig.getTwilioNumber()),
                    "Hi, found possible fire at " + compartmentName + " at " + floorDesc
            ).create();
            log.info("Sending SMS to " + message.getTo());
        }
    }

    private StatusReportLogDto buildSensorStatusReportLogDto(StatusReportLog statusReportLog) {
        StatusReportLogDto dto;
        EntityMapper<StatusReportLog, StatusReportLogDto> statusReportLogMapper = new StatusReportLogMapper();
        EntityMapper<SensorStatusReportLog, SensorStatusReportLogDto> sensorStatusMapper = new SensorStatusReportMapper();
        dto = statusReportLogMapper.mapToDto(statusReportLog);
        dto.setSensorStatusReportLogDtoList(statusReportLog.getSensorStatusReportLogs().stream()
                .map(sensorStatusMapper::mapToDto)
                .collect(Collectors.toList()));
        return dto;
    }

    public Resource buildPdf(ReportType reportType, Authentication authentication) {
        Resource resource = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        AccessToken accessToken = authenticationService.getKeycloakPrincipal(authentication);
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        try {
            PdfWriter pdfWriter = new PdfWriter(baos);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);

            //Append Logo
            buildLogoAndTitle(reportType, start, end, document);
            String requester = accessToken.getFamilyName() + ", " + accessToken.getGivenName();
            documentDetails(requester, document);
            document.close();

            final byte[] bytes = baos.toByteArray();
            resource = new ByteArrayResource(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (resource == null) {
            throw new CannotBuildPdfException("Error generating pdf");
        }
        return resource;
    }

    private void buildLogoAndTitle(ReportType reportType,
                                   LocalDateTime start, LocalDateTime end,
                                   Document document) throws MalformedURLException {
        /*
            Build Logo
         */
        ImageData logoData = ImageDataFactory.create(appConfig.getLogoPath());
        Image logo = new Image(logoData);
        logo.setHorizontalAlignment(HorizontalAlignment.CENTER);
        logo.scale(3.3f, 3.3f);
        document.add(logo);
        /*
            Build Title
         */
        Paragraph titleParagraph = new Paragraph(reportType.getType());
        titleParagraph.setPaddingTop(10f);
        titleParagraph.setHorizontalAlignment(HorizontalAlignment.CENTER);
        titleParagraph.setTextAlignment(TextAlignment.CENTER);
        titleParagraph.setFontSize(16f);
        document.add(titleParagraph);
        /*
            Date Range
         */
        Paragraph dateRange = new Paragraph(start.format(DateTimeFormatter.ISO_DATE)
                + " - "
                + end.format(DateTimeFormatter.ISO_DATE));
        dateRange.setHorizontalAlignment(HorizontalAlignment.CENTER);
        dateRange.setTextAlignment(TextAlignment.CENTER);
        dateRange.setUnderline(1f, -2f);
        document.add(dateRange);
    }

    private void documentDetails(String requester, Document document) {
        Paragraph reportGenerateParagraph = new Paragraph("Report generated by: " + requester);
        reportGenerateParagraph.setPaddingTop(5f);
        Paragraph reportRequestDate = new Paragraph("Report generated at " +
                ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME));
        reportRequestDate.setPaddingTop(5f);
        document.add(reportGenerateParagraph);
        document.add(reportRequestDate);
    }

    private void buildPFRTable() {

    }

    private void buildSRTable() {

    }
}
