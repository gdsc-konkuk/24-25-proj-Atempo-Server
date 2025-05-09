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

@Tag(name = "입원 요청", description = "입원 요청과 관련된 API")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/admissions")
public class AdmissionController {
    private final AdmissionService admissionService;
    private static final int METERS_PER_KILOMETER = 1000;

    @Operation(security = @SecurityRequirement(name = "JWT Auth"),
            summary = "입원 요청",
            description = "Header의 Authorization에 AccessToken을 담아 제출하면, 환자의 증상, 위치, 탐색 범위를 기반으로 병원을 검색 후 연락 결과를 반환합니다. - [관리자, 인증된 사용자] 기능"
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
            summary = "입원 재요청",
            description = "Header의 Authorization에 AccessToken을 담아 제출하면, 이전의 입원 요청을 기반으로 병원을 검색 후 연락 결과를 반환합니다. - [관리자, 인증된 사용자] 기능"
    )
    @PreAuthorize("hasAnyRole('ADMIN', VERIFIED)")
    @PostMapping("/{admissionId}/retry")
    public ResponseEntity<CreateAdmissionResponse> retryAdmissionWithRadius(@AuthenticationPrincipal UserDetails userDetails,
                                                                            @Parameter(description = "이전 입원 요청의 id")@PathVariable Long admissionId,
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
