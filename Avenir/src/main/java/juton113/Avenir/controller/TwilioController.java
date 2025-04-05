package juton113.Avenir.controller;

import juton113.Avenir.domain.dto.UpdateHospitalCallStatusDto;
import juton113.Avenir.service.HospitalCallStatusService;
import juton113.Avenir.service.TwilioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/twilio")
public class TwilioController {

    private final TwilioService twilioService;
    private final HospitalCallStatusService hospitalCallStatusService;

    @PostMapping("/voice-message")
    public String createVoiceMessage(@RequestParam("message") String voiceMessage) {
        return twilioService.createVoiceMessage(voiceMessage);
    }

    @PostMapping("/gather-response")
    public ResponseEntity<Void> handleGather(@RequestParam("Digits") String digit,
                                             @RequestParam("CallSid") String callSid) {
        UpdateHospitalCallStatusDto updateHospitalCallStatusDto = UpdateHospitalCallStatusDto.builder()
                .callId(callSid)
                .responseDigit(digit)
                .build();

        hospitalCallStatusService.updateHospitalCallStatus(updateHospitalCallStatusDto);

        return ResponseEntity.ok().build();
    }
}
