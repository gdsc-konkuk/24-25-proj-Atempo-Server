package juton113.Atempo.domain.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateMemberDto {
    private String provider;

    private String providerId;

    private String name;

    private String email;

    private String profileUrl;
}
