package juton113.Atempo.controller;

import juton113.Atempo.domain.dto.GetMemberResponseDto;
import juton113.Atempo.domain.dto.UpdateMemberDto;
import juton113.Atempo.domain.dto.UpdateMemberProfileRequestDto;
import juton113.Atempo.domain.dto.UpdateMemberRoleRequestDto;
import juton113.Atempo.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
public class MemberController {
    private final MemberService memberService;

    @GetMapping()
    public ResponseEntity<GetMemberResponseDto> getMember(@AuthenticationPrincipal UserDetails userDetails) {
        Long memberId = Long.parseLong(userDetails.getUsername());

        return ResponseEntity.ok(memberService.getMember(memberId));
    }

    @PutMapping("/profile")
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

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/role")
    public ResponseEntity<GetMemberResponseDto> updateMemberRole(@RequestBody UpdateMemberRoleRequestDto updateMemberRequestDto) {
        return ResponseEntity.ok(memberService.updateMemberRole(updateMemberRequestDto));
    }

}
