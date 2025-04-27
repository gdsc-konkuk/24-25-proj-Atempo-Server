package juton113.Atempo.domain.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMemberDto {
    Long memberId;
    String name;
    String nickName;
    String profileUrl;
}
