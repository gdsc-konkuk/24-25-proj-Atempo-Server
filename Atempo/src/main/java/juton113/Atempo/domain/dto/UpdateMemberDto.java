package juton113.Atempo.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMemberDto {
    @JsonProperty("member_id")
    Long memberId;
    String name;
    @JsonProperty("nick_name")
    String nickName;
    @JsonProperty("profile_url")
    String profileUrl;
}
