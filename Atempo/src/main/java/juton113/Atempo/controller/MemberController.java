package juton113.Atempo.controller;

import juton113.Atempo.domain.dto.GetMemberResponseDto;
import juton113.Atempo.domain.dto.UpdateMemberDto;
import juton113.Atempo.domain.dto.UpdateMemberRequestDto;
import juton113.Atempo.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PutMapping()
    public ResponseEntity<GetMemberResponseDto> updateMember(@AuthenticationPrincipal UserDetails userDetails,
                                                             @RequestBody UpdateMemberRequestDto updateMemberRequestDto) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        UpdateMemberDto updateMemberDto = UpdateMemberDto.builder()
                .memberId(memberId)
                .name(updateMemberRequestDto.getName())
                .nickName(updateMemberRequestDto.getNickName())
                .profileUrl(updateMemberRequestDto.getProfileUrl())
                .build();

        return ResponseEntity.ok(memberService.updateMember(updateMemberDto));
    }

}
