package juton113.Atempo.controller;

import juton113.Atempo.domain.dto.*;
import juton113.Atempo.service.AdmissionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/admissions")
public class AdmissionController {
    private final AdmissionService admissionService;

    @PreAuthorize("hasAnyRole('ADMIN', VERIFIED)")
    @PostMapping()
    public ResponseEntity<CreateAdmissionResponse> createAdmission(@AuthenticationPrincipal UserDetails userDetails,
                                                                   @RequestBody CreateAdmissionRequest createAdmissionRequest) {
        Long memberId = Long.parseLong(userDetails.getUsername());

        CreateAdmissionDto createAdmissionDto = CreateAdmissionDto
                .builder()
                .memberId(memberId)
                .location(createAdmissionRequest.getLocation())
                .searchRadius(createAdmissionRequest.getSearchRadius())
                .patientCondition(createAdmissionRequest.getPatientCondition())
                .build();

        return ResponseEntity.ok(admissionService.createAdmissionCall(createAdmissionDto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', VERIFIED)")
    @PostMapping("/{admissionId}/retry")
    public ResponseEntity<CreateAdmissionResponse> retryAdmissionWithRadius(@AuthenticationPrincipal UserDetails userDetails,
                                                      @PathVariable Long admissionId,
                                                      @RequestBody RetryAdmissionRequest retryAdmissionRequest) {
        Long memberId = Long.parseLong(userDetails.getUsername());

        RetryAdmissionDto retryAdmissionDto = RetryAdmissionDto.builder()
                .memberId(memberId)
                .originalAdmissionId(admissionId)
                .location(retryAdmissionRequest.getLocation())
                .searchRadius(retryAdmissionRequest.getSearchRadius())
                .build();

        return ResponseEntity.ok(admissionService.retryAdmissionCallByRadius(retryAdmissionDto));
    }

}
