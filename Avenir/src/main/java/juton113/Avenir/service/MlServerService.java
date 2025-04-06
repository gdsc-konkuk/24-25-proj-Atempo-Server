package juton113.Avenir.service;

import jakarta.transaction.Transactional;
import juton113.Avenir.domain.dto.AdmissionDataRequestDto;
import juton113.Avenir.domain.dto.AdmissionDataResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class MlServerService {
    @Value("${origin.ml}")
    private String mlServerUrl;
    public AdmissionDataResponseDto requestAdmissionData(AdmissionDataRequestDto request) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AdmissionDataRequestDto> entity = new HttpEntity<>(request, headers);

        ResponseEntity<AdmissionDataResponseDto> response = restTemplate.exchange(
                mlServerUrl,
                HttpMethod.POST,
                entity,
                AdmissionDataResponseDto.class
        );

        return response.getBody();
    }

}
