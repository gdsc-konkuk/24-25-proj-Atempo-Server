package juton113.Avenir.service;

import jakarta.transaction.Transactional;
import juton113.Avenir.domain.dto.CreateHospitalCallStatusDto;
import juton113.Avenir.domain.dto.UpdateHospitalCallStatusDto;
import juton113.Avenir.domain.entity.HospitalCallStatus;
import juton113.Avenir.domain.enums.CallResponseStatus;
import juton113.Avenir.domain.enums.CallStatus;
import juton113.Avenir.domain.enums.ErrorCode;
import juton113.Avenir.exception.CustomException;
import juton113.Avenir.repository.HospitalCallStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class HospitalCallStatusService {
    private final HospitalCallStatusRepository hospitalCallStatusRepository;

    @Transactional
    public void createHospitalCallStatus(CreateHospitalCallStatusDto createHospitalCallStatusDto) {
        HospitalCallStatus hospitalCallStatus = HospitalCallStatus.builder()
                .callId(createHospitalCallStatusDto.getCallId())
                .hospital(createHospitalCallStatusDto.getHospital())
                .callStatus(createHospitalCallStatusDto.getCallStatus())
                .callResponseStatus(createHospitalCallStatusDto.getCallResponseStatus())
                .callAttempts(createHospitalCallStatusDto.getCallAttempts())
                .build();

        hospitalCallStatusRepository.save(hospitalCallStatus);
    }

    @Transactional
    public void updateHospitalCallStatus(UpdateHospitalCallStatusDto updateHospitalCallStatusDto) {
        HospitalCallStatus callStatus = hospitalCallStatusRepository.findByCallId(updateHospitalCallStatusDto.getCallId()).orElseThrow(
                () -> new CustomException(ErrorCode.HOSPITAL_CALL_STATUS_NOT_FOUND));

        CallResponseStatus response = CallResponseStatus.REJECTED;

        if(updateHospitalCallStatusDto.getResponseDigit().equals("1"))
            response = CallResponseStatus.ACCEPTED;

        callStatus.update(CallStatus.ANSWERED, response);

        // TODO SSE 적용할 것

    }
}
