package juton113.Atempo.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import juton113.Atempo.domain.enums.Role;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMemberRoleRequestDto {
    @Schema(example = "1")
    @JsonProperty("member_id")
    Long memberId;

    @Schema(example = "ADMIN")
    Role role;
}
