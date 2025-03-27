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

    public AccessTokenDto reissueAccessToken(String refreshToken) {
        Claims claims = tokenService.parseToken(refreshToken);
        tokenService.validateTokenType(claims, TokenType.REFRESH);

        String memberId = claims.getSubject();
        redisService.validateStoredToken(memberId, refreshToken);

        return issueAccessToken(memberId);
    }

    public RefreshTokenDto reissueRefreshToken(String refreshToken) {
        Claims claims = tokenService.parseToken(refreshToken);
        tokenService.validateTokenType(claims, TokenType.REFRESH);

        String memberId = claims.getSubject();
        redisService.validateStoredToken(memberId, refreshToken);

        return issueRefreshToken(memberId);
    }

    public AccessTokenDto issueAccessToken(String memberId) {
        String newAccessToken = tokenService.createAccessToken(memberId);

        return new AccessTokenDto(newAccessToken);
    }

    public RefreshTokenDto issueRefreshToken(String memberId) {
        String newRefreshToken = tokenService.createRefreshToken(memberId);
        redisService.saveToken(memberId, newRefreshToken);

        return new RefreshTokenDto(newRefreshToken);
    }
}
