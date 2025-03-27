package juton113.Avenir.security.oauth;

import juton113.Avenir.domain.dto.CreateMemberDto;
import juton113.Avenir.domain.entity.Member;
import juton113.Avenir.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberService memberService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();

        OAuthAttributes attributes = OAuthAttributes.of(
                provider,
                oAuth2User.getAttributes()
        );

        CreateMemberDto createMemberDto = CreateMemberDto.builder()
                .provider(provider)
                .providerId(attributes.getProviderId())
                .name(attributes.getName())
                .email(attributes.getEmail())
                .profileUrl(attributes.getPicture())
                .build();

        Member member = memberService.findOrCreateMember(createMemberDto);

        return new CustomOAuth2User(member, oAuth2User.getAttributes());
    }
}
