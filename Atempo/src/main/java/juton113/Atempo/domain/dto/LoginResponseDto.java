package juton113.Atempo.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
    @Schema(example = "OAuth 로그인 URL 반환")
    private String message;
    @Schema(example = "/oauth2/authorization/google")
    private String loginUrl;
}
