package juton113.Avenir.service;

import com.twilio.http.HttpMethod;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Gather;
import com.twilio.twiml.voice.Say;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class TwilioService {
    @Value("${origin.be}")
    private String beServerUrl;

    @Value("${twilio.from-phone-number}")
    private String fromPhoneNumber;

    public String createCall(String toPhoneNumber, String arsMessage) {
        URI voiceUrl = URI.create(beServerUrl + "/api/v1/twilio/voice-message?message=" + URLEncoder.encode(arsMessage, StandardCharsets.UTF_8));

        Call call = Call.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(fromPhoneNumber),
                voiceUrl
        ).create();

        return call.getSid();
    }

    public String createVoiceMessage(String voiceMessage) {
        Say say = new Say.Builder(voiceMessage)
                .voice(Say.Voice.ALICE)
                .language(Say.Language.KO_KR)
                .build();

        Gather gather = new Gather.Builder()
                .say(say)
                .numDigits(1)
                .action(beServerUrl + "/api/v1/twilio/gather-response")
                .method(HttpMethod.POST)
                .timeout(60)
                .build();

        VoiceResponse response = new VoiceResponse.Builder()
                .gather(gather)
                .build();

        return response.toXml();
    }

}
