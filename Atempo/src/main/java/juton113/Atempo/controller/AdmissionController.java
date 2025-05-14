package juton113.Atempo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import juton113.Atempo.domain.dto.*;
import juton113.Atempo.service.AdmissionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Admission Request", description = "Admission Request APIs")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/admissions")
public class AdmissionController {
    private final AdmissionService admissionService;
    private static final int METERS_PER_KILOMETER = 1000;

    @Operation(security = @SecurityRequirement(name = "JWT Auth"),
            summary = "Admission Request",
            description = "Searches for hospitals based on the patient's symptoms, location, and search radius, then returns the result of the contact attempt. - [Admins, Verified Users Only]"
    )
    @PreAuthorize("hasAnyRole('ADMIN', VERIFIED)")
    @PostMapping("")
    public ResponseEntity<CreateAdmissionResponse> createAdmission(@AuthenticationPrincipal UserDetails userDetails,
                                                                   @RequestBody CreateAdmissionRequest createAdmissionRequest) {
        Long memberId = Long.parseLong(userDetails.getUsername());

        CreateAdmissionDto createAdmissionDto = CreateAdmissionDto
                .builder()
                .memberId(memberId)
                .location(createAdmissionRequest.getLocation())
                .searchRadius(createAdmissionRequest.getSearchRadius() * METERS_PER_KILOMETER)
                .patientCondition(createAdmissionRequest.getPatientCondition())
                .build();

        return ResponseEntity.ok(admissionService.createAdmissionCall(createAdmissionDto));
    }

    @Operation(security = @SecurityRequirement(name = "JWT Auth"),
            summary = "Re-admission Request",
            description = "Searches for hospitals based on the previous admission request and returns the result of the contact attempt. – [Admins, Verified Users Only]"
    )
    @PreAuthorize("hasAnyRole('ADMIN', VERIFIED)")
    @PostMapping("/{admissionId}/retry")
    public ResponseEntity<CreateAdmissionResponse> retryAdmissionWithRadius(@AuthenticationPrincipal UserDetails userDetails,
                                                                            @Parameter(description = "previous admission request id")@PathVariable Long admissionId,
                                                                            @RequestBody RetryAdmissionRequest retryAdmissionRequest) {
        Long memberId = Long.parseLong(userDetails.getUsername());

        RetryAdmissionDto retryAdmissionDto = RetryAdmissionDto.builder()
                .memberId(memberId)
                .originalAdmissionId(admissionId)
                .location(retryAdmissionRequest.getLocation())
                .searchRadius(retryAdmissionRequest.getSearchRadius() * METERS_PER_KILOMETER)
                .build();

        return ResponseEntity.ok(admissionService.retryAdmissionCallByRadius(retryAdmissionDto));
    }

}
