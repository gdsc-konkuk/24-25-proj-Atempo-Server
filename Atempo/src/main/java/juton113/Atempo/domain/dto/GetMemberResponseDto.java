package juton113.Atempo.domain.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMemberResponseDto {
    String name;
    String nickName;
    String email;
    String profileUrl;
}
