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

@Tag(name = "사용자 정보", description = "생성, 조회, 수정, 삭제와 관련되 API")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
public class MemberController {
    private final MemberService memberService;

    @Operation(security = @SecurityRequirement(name = "JWT Auth"),
            summary = "사용자 정보 조회",
            description = "Header의 Authorization에 AccessToken을 담아 제출하면, 해당 사용자의 정보를 반환합니다."
    )
    @GetMapping("")
    public ResponseEntity<GetMemberResponseDto> getMember(@AuthenticationPrincipal UserDetails userDetails) {
        Long memberId = Long.parseLong(userDetails.getUsername());

        return ResponseEntity.ok(memberService.getMember(memberId));
    }

    @Operation(security = @SecurityRequirement(name = "JWT Auth"),
            summary = "사용자 정보 수정",
            description = "Header의 Authorization에 AccessToken을 담아 제출하면, 해당 사용자의 정보를 수정 후 정보를 반환합니다."
    )
    @PutMapping("/profile") // TODO: fix http method PUT -> PATCH
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
            summary = "자격증 정보 수정",
            description = "Header의 Authorization에 AccessToken을 담아 제출하면, 해당 사용자의 자격증을 수정 후 정보를 반환합니다."
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
            summary = "사용자 권한 수정",
            description = "Header의 Authorization에 AccessToken을 담아 제출하면, 해당 사용자의 권한을 수정 후 정보를 반환합니다. - [관리자] 기능"
    )
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/role") // TODO: fix http method PUT -> PATCH
    public ResponseEntity<GetMemberResponseDto> updateMemberRole(@RequestBody UpdateMemberRoleRequestDto updateMemberRequestDto) {
        return ResponseEntity.ok(memberService.updateMemberRole(updateMemberRequestDto));
    }

}
