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
public class GetMemberResponseDto {
    @Schema(example = "tester_name")
    String name;

    @Schema(example = "tester_nick_name")
    @JsonProperty("nick_name")
    String nickName;

    @Schema(example = "tester@gmail.com")
    String email;

    @Schema(example = "https://lh3.googleusercontent.com/a/abcd...")
    @JsonProperty("profile_url")
    String profileUrl;

    @Schema(example = "ADMIN")
    Role role;
}
