package juton113.Atempo.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMemberProfileRequestDto {
    @Schema(example = "tester")
    String name;

    @Schema(example = "tester_nick_name")
    @JsonProperty("nick_name")
    String nickName;

    @Schema(example = "https://lh3.googleusercontent.com/a/abcd...")
    @JsonProperty("profile_url")
    String profileUrl;
}
