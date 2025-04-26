package juton113.Atempo.service;

import juton113.Atempo.domain.dto.HospitalResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SseService {
    private final Long connectionTime;
    private final Map<Long, SseEmitter> emitters;

    public SseService(@Value("${sse.connection-time}") Long connectionTime) {
        this.connectionTime = connectionTime;
        this.emitters = new ConcurrentHashMap<>();
    }

    public SseEmitter subscribe(Long memberId) {
        SseEmitter emitter = new SseEmitter(connectionTime);
        emitters.put(memberId, emitter);

        emitter.onCompletion(() -> emitters.remove(memberId));
        emitter.onTimeout(() -> emitters.remove(memberId));
        emitter.onError(e -> emitters.remove(memberId));

        try {
            emitter.send(SseEmitter.event()
                    .name("INIT")
                    .data("connected"));
        } catch (IOException e) {
            emitter.completeWithError(e);
        }

        return emitter;
    }

    public void sendHospitalInfo(Long memberId, HospitalResponseDto hospitalResponseDto) {
        SseEmitter emitter = emitters.get(memberId);

        if(emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name("HOSPITAL_RESPONSE")
                        .data(hospitalResponseDto));
            } catch (IOException e) {
                emitter.completeWithError(e);
                emitters.remove(memberId);
            }
        }
    }
}
