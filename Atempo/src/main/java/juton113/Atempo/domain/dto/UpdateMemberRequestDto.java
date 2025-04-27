package juton113.Atempo.domain.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMemberRequestDto {
    String name;
    String nickName;
    String profileUrl;
}
