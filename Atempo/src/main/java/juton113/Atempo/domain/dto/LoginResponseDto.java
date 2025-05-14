package juton113.Atempo.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
    @Schema(example = "Returns OAuth login endpoint.")
    private String message;
    @Schema(example = "/oauth2/authorization/google")
    private String loginUrl;
}
