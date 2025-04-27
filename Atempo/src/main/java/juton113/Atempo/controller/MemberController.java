package juton113.Atempo.controller;

import juton113.Atempo.domain.dto.GetMemberResponseDto;
import juton113.Atempo.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/{memberId}")
    public ResponseEntity<GetMemberResponseDto> getMember(@PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(memberService.getMember(memberId));
    }

}
