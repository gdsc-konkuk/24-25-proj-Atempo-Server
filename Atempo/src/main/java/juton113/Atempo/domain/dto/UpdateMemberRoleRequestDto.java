package juton113.Atempo.domain.dto;

import juton113.Atempo.domain.enums.Role;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMemberRoleRequestDto {
    Long memberId;
    Role role;
}
