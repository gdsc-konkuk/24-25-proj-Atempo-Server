package juton113.Atempo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import juton113.Atempo.domain.dto.*;
import juton113.Atempo.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member Info", description = "APIs related to member CRUD")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
public class MemberController {
    private final MemberService memberService;

    @Operation(security = @SecurityRequirement(name = "JWT Auth"),
            summary = "Get User Information",
            description = "Returns the user's information."
    )
    @GetMapping("")
    public ResponseEntity<GetMemberResponseDto> getMember(@AuthenticationPrincipal UserDetails userDetails) {
        Long memberId = Long.parseLong(userDetails.getUsername());

        return ResponseEntity.ok(memberService.getMember(memberId));
    }

    @Operation(security = @SecurityRequirement(name = "JWT Auth"),
            summary = "Update User Information",
            description = "Updates the user's information and returns the updated data."
    )
    @PatchMapping("/profile")
    public ResponseEntity<GetMemberResponseDto> updateMemberProfile(@AuthenticationPrincipal UserDetails userDetails,
                                                             @RequestBody UpdateMemberProfileRequestDto updateMemberRequestDto) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        UpdateMemberDto updateMemberDto = UpdateMemberDto.builder()
                .memberId(memberId)
                .name(updateMemberRequestDto.getName())
                .nickName(updateMemberRequestDto.getNickName())
                .profileUrl(updateMemberRequestDto.getProfileUrl())
                .build();

        return ResponseEntity.ok(memberService.updateMemberProfile(updateMemberDto));
    }

    @Operation(security = @SecurityRequirement(name = "JWT Auth"),
            summary = "Update Certification Information",
            description = "Updates the user's certification information and returns the updated data."
    )
    @PatchMapping("/certification")
    public ResponseEntity<GetMemberResponseDto> updateMemberCertificationInfo(@AuthenticationPrincipal UserDetails userDetails,
                                                                              @RequestBody UpdateMemberCertificationInfoRequest updateMemberCertificationInfoRequest) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        UpdateMemberCertificationInfoDto updateMemberCertificationInfoDto = UpdateMemberCertificationInfoDto.builder()
                .memberId(memberId)
                .certificationType(updateMemberCertificationInfoRequest.getCertificationType())
                .certificationNumber(updateMemberCertificationInfoRequest.getCertificationNumber())
                .build();

        return ResponseEntity.ok(memberService.updateMemberCertification(updateMemberCertificationInfoDto));
    }

    @Operation(security = @SecurityRequirement(name = "JWT Auth"),
            summary = "Update User Role",
            description = "Updates the user's role and returns the updated data. – [Admin Only]"
    )
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("/role")
    public ResponseEntity<GetMemberResponseDto> updateMemberRole(@RequestBody UpdateMemberRoleRequestDto updateMemberRequestDto) {
        return ResponseEntity.ok(memberService.updateMemberRole(updateMemberRequestDto));
    }

}
