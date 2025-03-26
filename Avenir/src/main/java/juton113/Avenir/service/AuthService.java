package juton113.Avenir.service;

import io.jsonwebtoken.Claims;
import juton113.Avenir.domain.dto.AccessTokenDto;
import juton113.Avenir.domain.dto.RefreshTokenDto;
import juton113.Avenir.domain.enums.TokenType;
import juton113.Avenir.security.jwt.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final TokenService tokenService;
    private final RedisService redisService;

    public AccessTokenDto issueAccessToken(String refreshToken) {
        Claims claims = tokenService.parseToken(refreshToken);
        tokenService.validateTokenType(claims, TokenType.REFRESH);

        String memberId = claims.getSubject();
        redisService.validateStoredToken(memberId, refreshToken);

        String accessToken = tokenService.createAccessToken(memberId);

        return new AccessTokenDto(accessToken);
    }

    public RefreshTokenDto issueRefreshToken(String refreshToken) {
        Claims claims = tokenService.parseToken(refreshToken);
        tokenService.validateTokenType(claims, TokenType.REFRESH);

        String memberId = claims.getSubject();
        redisService.validateStoredToken(memberId, refreshToken);

        String newRefreshToken = tokenService.createRefreshToken(memberId);

        return new RefreshTokenDto(newRefreshToken);
    }
}
