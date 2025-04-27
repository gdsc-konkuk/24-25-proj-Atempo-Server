package juton113.Atempo.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import juton113.Atempo.domain.enums.Role;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMemberResponseDto {
    String name;
    @JsonProperty("nick_name")
    String nickName;
    String email;
    @JsonProperty("profile_url")
    String profileUrl;
    Role role;
}
