package juton113.Atempo.service;

import jakarta.transaction.Transactional;
import juton113.Atempo.domain.dto.*;
import juton113.Atempo.domain.dto.common.HospitalInfo;
import juton113.Atempo.domain.entity.Admission;
import juton113.Atempo.domain.entity.Hospital;
import juton113.Atempo.domain.entity.Member;
import juton113.Atempo.domain.enums.CallResponseStatus;
import juton113.Atempo.domain.enums.CallStatus;
import juton113.Atempo.domain.enums.ErrorCode;
import juton113.Atempo.exception.CustomException;
import juton113.Atempo.repository.AdmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdmissionService {
    private final MemberService memberService;
    private final AdmissionMessageService admissionMessageService;
    private final MlServerService mlServerService;
    private final HospitalService hospitalService;
    private final HospitalCallStatusService hospitalCallStatusService;
    private final TwilioService twilioService;

    private final AdmissionRepository admissionRepository;

    @Transactional
    public CreateAdmissionResponse createAdmissionCall(CreateAdmissionDto createAdmissionDto) {
        Member member = memberService.findByMemberId(createAdmissionDto.getMemberId());

        Admission admission = Admission.of(
                member,
                createAdmissionDto.getLocation(),
                createAdmissionDto.getSearchRadius(),
                createAdmissionDto.getPatientCondition(),
                null);

        processAdmissionCall(admission);

        admissionRepository.save(admission);

        return CreateAdmissionResponse.builder().admissionId(admission.getAdmissionId()).build();
    }

    @Transactional
    public CreateAdmissionResponse retryAdmissionCallByRadius(RetryAdmissionDto retryAdmissionDto) {
        Member member = memberService.findByMemberId(retryAdmissionDto.getMemberId());
        Long originalAdmissionId = retryAdmissionDto.getOriginalAdmissionId();
        Admission originalAdmission = admissionRepository.findById(originalAdmissionId).orElseThrow(() -> new CustomException(ErrorCode.ADMISSION_NOT_FOUND));

        Admission admission = Admission.of(
                member,
                retryAdmissionDto.getLocation(),
                retryAdmissionDto.getSearchRadius(),
                originalAdmission.getPatientCondition(),
                originalAdmissionId);

        processAdmissionCall(admission);

        admissionRepository.save(admission);

        return CreateAdmissionResponse.builder().admissionId(admission.getAdmissionId()).build();
    }

    private void processAdmissionCall(Admission admission) {
        // request hospital list and ars message to ml-server
        MlCreateAdmissionRequest request = MlCreateAdmissionRequest.builder()
                .location(admission.getLocation())
                .searchRadius(admission.getSearchRadius())
                .patientCondition(admission.getPatientCondition())
                .build();
//        MlCreateAdmissionResponse response = mlServerService.requestAdmissionData(request);
        // TODO:  실제 서비스 시, 위의 주석을 해제하고 requestAdmissionMockData를 호출하는 라인은 지울 것
        MlCreateAdmissionResponse response = mlServerService.requestAdmissionMockData(request);

        List<HospitalInfo> hospitalInfoList = response.getHospitalList();
        String arsMessage = response.getArsMessage();

        // create admission message entity
        CreateAdmissionMessageDto createAdmissionMessageDto = CreateAdmissionMessageDto.builder()
                .admission(admission)
                .message(arsMessage)
                .build();
        admissionMessageService.createAdmissionMessage(createAdmissionMessageDto);

        // request twilio call
        for(HospitalInfo hospitalInfo : hospitalInfoList) {
            String hospitalPhoneNumber = hospitalInfo.getPhoneNumber();

//            String callId = twilioService.createCall(hospitalNumber, arsMessage);
            // TODO: 실제 서비스 시, 위의 주석을 해제하고 createMockCall를 호출하는 라인은 지울 것
            String callId = twilioService.createMockCall(hospitalPhoneNumber, arsMessage);

            CreateHospitalDto createHospitalDto = CreateHospitalDto.builder()
                    .admission(admission)
                    .name(hospitalInfo.getName())
                    .phoneNumber(hospitalInfo.getPhoneNumber())
                    .address(hospitalInfo.getAddress())
                    .distance(hospitalInfo.getDistance())
                    .travelTime(hospitalInfo.getTravelTime())
                    .departments(String.join(",", hospitalInfo.getDepartments()))
                    .build();

            // create hospital entity
            Hospital hospital = hospitalService.createHospital(createHospitalDto);

            // create hospitalCallStatus entity
            CreateHospitalCallStatusDto createHospitalCallStatusDto = CreateHospitalCallStatusDto.builder()
                    .callId(callId)
                    .hospital(hospital)
                    .callStatus(CallStatus.NO_ANSWER)
                    .callResponseStatus(CallResponseStatus.NO_RESPONSE)
                    .callAttempts(0)
                    .build();

            hospitalCallStatusService.createHospitalCallStatus(createHospitalCallStatusDto);
        }
    }
}
