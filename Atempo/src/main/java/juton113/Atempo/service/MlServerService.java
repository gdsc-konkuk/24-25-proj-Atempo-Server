package juton113.Atempo.service;

import juton113.Atempo.domain.dto.AdmissionDataRequestDto;
import juton113.Atempo.domain.dto.AdmissionDataResponseDto;
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
