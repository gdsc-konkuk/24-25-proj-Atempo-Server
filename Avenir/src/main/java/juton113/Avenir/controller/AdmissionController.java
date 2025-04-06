package juton113.Avenir.controller;

import juton113.Avenir.domain.dto.AdmissionDataRequestTestDto;
import juton113.Avenir.domain.dto.CreateAdmissionDto;
import juton113.Avenir.domain.dto.AdmissionDataRequestDto;
import juton113.Avenir.service.AdmissionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/admissions")
public class AdmissionController {
    private final AdmissionService admissionService;;

    @PreAuthorize("hasAnyRole('ADMIN', VERIFIED)")
    @PostMapping
    public ResponseEntity<?> createAdmission(@RequestBody AdmissionDataRequestDto admissionDataRequestDto,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        Long memberId = Long.parseLong(userDetails.getUsername());

        CreateAdmissionDto createAdmissionDto = CreateAdmissionDto
                .builder()
                .memberId(memberId)
                .latitude(admissionDataRequestDto.getLatitude())
                .longitude(admissionDataRequestDto.getLongitude())
                .patientCondition(admissionDataRequestDto.getPatientCondition())
                .build();

        admissionService.createAdmissionCall(createAdmissionDto);

        return ResponseEntity.ok().body("admission request success");
    }

    @PreAuthorize("hasAnyRole('ADMIN', VERIFIED)")
    @PostMapping("/test")
    public ResponseEntity<?> createAdmissionTest(@RequestBody AdmissionDataRequestTestDto admissionDataRequestTestDto,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        admissionService.createAdmissionCallTest(memberId, admissionDataRequestTestDto);

        return ResponseEntity.ok().body("admission request success");
    }

}
