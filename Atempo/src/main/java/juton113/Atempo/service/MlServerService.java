package juton113.Atempo.service;

import jakarta.transaction.Transactional;
import juton113.Atempo.domain.dto.MlCreateAdmissionRequest;
import juton113.Atempo.domain.dto.MlCreateAdmissionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MlServerService {
    @Value("${origin.ml}")
    private String mlServerUrl;

    @Transactional
    public MlCreateAdmissionResponse requestAdmissionData(MlCreateAdmissionRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MlCreateAdmissionRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<MlCreateAdmissionResponse> response = restTemplate.exchange(
                mlServerUrl,
                HttpMethod.POST,
                entity,
                MlCreateAdmissionResponse.class
        );

        return response.getBody();
    }

}
