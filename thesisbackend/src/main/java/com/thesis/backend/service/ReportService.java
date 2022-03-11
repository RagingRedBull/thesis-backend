package com.thesis.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import java.util.Locale;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportService {
    public void playFireWarning(String compartmentName) {
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
                        "WARNING! FIRE DETECTED AT " + compartmentName, null);
                Thread.sleep(1000);
            }
            synthesizer.waitEngineState(
                    Synthesizer.QUEUE_EMPTY);
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
