package juton113.Avenir.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private String message;
    private String loginUrl;
}
