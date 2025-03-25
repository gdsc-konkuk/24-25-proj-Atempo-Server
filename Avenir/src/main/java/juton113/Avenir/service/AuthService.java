package juton113.Avenir.service;

import juton113.Avenir.domain.dto.AccessTokenDto;
import juton113.Avenir.domain.dto.RefreshTokenDto;
import juton113.Avenir.security.jwt.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final TokenService tokenService;
    private final RedisService redisService;

    public AccessTokenDto issueAccessToken(String subject) {
        String accessToken = tokenService.createAccessToken(subject);

        return new AccessTokenDto(accessToken);
    }

    public RefreshTokenDto issueRefreshToken(String subject) {
        String refreshToken = tokenService.createRefreshToken(subject);
        redisService.saveToken(subject, refreshToken);

        return new RefreshTokenDto(refreshToken);
    }
}
