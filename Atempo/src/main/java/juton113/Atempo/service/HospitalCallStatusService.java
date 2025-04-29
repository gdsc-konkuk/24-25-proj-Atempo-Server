package juton113.Atempo.service;

import jakarta.transaction.Transactional;
import juton113.Atempo.domain.dto.CreateHospitalCallStatusDto;
import juton113.Atempo.domain.dto.HospitalResponseDto;
import juton113.Atempo.domain.dto.UpdateHospitalCallStatusDto;
import juton113.Atempo.domain.entity.Hospital;
import juton113.Atempo.domain.entity.HospitalCallStatus;
import juton113.Atempo.domain.enums.CallResponseStatus;
import juton113.Atempo.domain.enums.CallStatus;
import juton113.Atempo.domain.enums.ErrorCode;
import juton113.Atempo.exception.CustomException;
import juton113.Atempo.repository.HospitalCallStatusRepository;
import juton113.Atempo.repository.HospitalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class HospitalCallStatusService {
    private final HospitalCallStatusRepository hospitalCallStatusRepository;
    private final HospitalRepository hospitalRepository;
    private final SseService sseService;

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

        String digit = updateHospitalCallStatusDto.getResponseDigit();
        CallResponseStatus response = digit.equals("1") ? CallResponseStatus.ACCEPTED : CallResponseStatus.REJECTED;

        callStatus.update(CallStatus.ANSWERED, response);

        if(!digit.equals("1")) return;

        Hospital hospital = callStatus.getHospital();
        HospitalResponseDto hospitalResponseDto = HospitalResponseDto.builder()
                .name(hospital.getName())
                .phoneNumber(hospital.getPhoneNumber())
                .address(hospital.getAddress())
                .distance(hospital.getDistance())
                .travelTime(hospital.getTravelTime())
                .departments(hospital.getDepartments())
                .build();
        Long memberId = hospital.getAdmission().getAdmissionId();
        sseService.sendHospitalInfo(memberId, hospitalResponseDto);

    }
}
