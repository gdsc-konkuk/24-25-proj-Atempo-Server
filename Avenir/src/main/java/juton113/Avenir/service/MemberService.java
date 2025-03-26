package juton113.Avenir.service;

import juton113.Avenir.domain.dto.CreateMemberDto;
import juton113.Avenir.domain.entity.Member;
import juton113.Avenir.domain.enums.ErrorCode;
import juton113.Avenir.domain.enums.Role;
import juton113.Avenir.exception.CustomException;
import juton113.Avenir.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
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
                .role(Role.MEMBER)
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
