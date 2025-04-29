package juton113.Atempo.service;

import jakarta.transaction.Transactional;
import juton113.Atempo.domain.dto.MlCreateAdmissionRequest;
import juton113.Atempo.domain.dto.MlCreateAdmissionResponse;
import juton113.Atempo.domain.dto.common.HospitalInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

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

    public MlCreateAdmissionResponse requestAdmissionMockData(MlCreateAdmissionRequest request) {
        MlCreateAdmissionResponse mlCreateAdmissionResponse = new MlCreateAdmissionResponse();
        List<HospitalInfo> hospitalList = new ArrayList<>();

        String name = "test_hospital_name";
        String phoneNumber = "+8210112345678";
        String address = "test_address";
        double distance = 10;
        Integer travelTime = 20;
        List<String> departments = new ArrayList<>();
        departments.add("test_department1");
        departments.add("test_department2");
        departments.add("test_department3");

        hospitalList.add(new HospitalInfo(name, phoneNumber, address, distance, travelTime, departments));
        hospitalList.add(new HospitalInfo(name, phoneNumber, address, distance, travelTime, departments));

        mlCreateAdmissionResponse.setHospitalList(hospitalList);
        mlCreateAdmissionResponse.setArsMessage("test_ars_message");

        return mlCreateAdmissionResponse;
    }

}
