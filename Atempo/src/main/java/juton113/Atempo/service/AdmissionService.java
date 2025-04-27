package juton113.Atempo.service;

import jakarta.transaction.Transactional;
import juton113.Atempo.domain.dto.*;
import juton113.Atempo.domain.entity.Admission;
import juton113.Atempo.domain.entity.Hospital;
import juton113.Atempo.domain.entity.Member;
import juton113.Atempo.domain.enums.CallResponseStatus;
import juton113.Atempo.domain.enums.CallStatus;
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
    public void createAdmissionCall(CreateAdmissionDto createAdmissionDto) {
        // create save admission entity
        Admission admission = createAdmission(createAdmissionDto);

        // request hospital list and ars message to ml-server
        AdmissionDataRequestDto requestDto = AdmissionDataRequestDto.builder()
                .latitude(createAdmissionDto.getLatitude())
                .longitude(createAdmissionDto.getLongitude())
                .patientCondition(createAdmissionDto.getPatientCondition())
                .build();
        AdmissionDataResponseDto response = mlServerService.requestAdmissionData(requestDto);

        List<CreateHospitalResponseDto> hospitalList = response.getHospitalList();
        String arsMessage = response.getArsMessage();

        // create admission message entity
        CreateAdmissionMessageDto createAdmissionMessageDto = CreateAdmissionMessageDto.builder()
                .admission(admission)
                .message(arsMessage)
                .build();
        admissionMessageService.createAdmissionMessage(createAdmissionMessageDto);

        // request twilio call
        for(CreateHospitalResponseDto createHospitalResponseDto : hospitalList) {
            String hospitalNumber = createHospitalResponseDto.getPhoneNumber();
            String callId = twilioService.createCall(hospitalNumber, arsMessage);

            CreateHospitalDto createHospitalDto = CreateHospitalDto.builder()
                    .admission(admission)
                    .name(createHospitalResponseDto.getName())
                    .phoneNumber(createHospitalResponseDto.getPhoneNumber())
                    .address(createHospitalResponseDto.getAddress())
                    .distance(createHospitalResponseDto.getDistance())
                    .travelTime(createHospitalResponseDto.getTravelTime())
                    .departments(String.join(",", createHospitalResponseDto.getDepartments()))
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

    @Transactional
    public Admission createAdmission(CreateAdmissionDto createAdmissionDto) {
        Member member = memberService.findByMemberId(createAdmissionDto.getMemberId());

        Admission admission = Admission.builder()
                .member(member)
                .latitude(createAdmissionDto.getLatitude())
                .longitude(createAdmissionDto.getLongitude())
                .patientCondition(createAdmissionDto.getPatientCondition())
                .build();

        return admissionRepository.save(admission);
    }
}
