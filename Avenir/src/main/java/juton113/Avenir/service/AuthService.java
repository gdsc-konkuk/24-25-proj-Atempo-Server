package juton113.Avenir.service;

import juton113.Avenir.domain.dto.TokenResponseDto;
import juton113.Avenir.security.jwt.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final TokenService tokenService;
    private final RedisService redisService;

    public TokenResponseDto issueToken(String subject) {
        String accessToken = tokenService.createAccessToken(subject);
        String refreshToken = tokenService.createRefreshToken(subject);

        redisService.saveToken(subject, refreshToken);

        return new TokenResponseDto(accessToken, refreshToken);
    }
}
