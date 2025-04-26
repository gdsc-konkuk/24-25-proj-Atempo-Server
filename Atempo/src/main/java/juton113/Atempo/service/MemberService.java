package juton113.Atempo.service;

import jakarta.transaction.Transactional;
import juton113.Atempo.domain.dto.CreateMemberDto;
import juton113.Atempo.domain.entity.Member;
import juton113.Atempo.domain.enums.ErrorCode;
import juton113.Atempo.domain.enums.Role;
import juton113.Atempo.exception.CustomException;
import juton113.Atempo.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Member findOrCreateMember(CreateMemberDto createMemberDto) {
        String email = createMemberDto.getEmail();
        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        if(optionalMember.isPresent()) {
            return optionalMember.get();
        }

        Member member = Member.builder()
                .provider(createMemberDto.getProvider())
                .providerId(createMemberDto.getProviderId())
                .name(createMemberDto.getName())
                .email(email)
                .profileUrl(createMemberDto.getProfileUrl())
                .role(Role.BASIC)
                .build();

        return memberRepository.save(member);
    }

    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public Member findByMemberId(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
