package juton113.Atempo.service;

import io.jsonwebtoken.Claims;
import juton113.Atempo.domain.dto.AccessTokenDto;
import juton113.Atempo.domain.dto.RefreshTokenDto;
import juton113.Atempo.domain.enums.TokenType;
import juton113.Atempo.security.jwt.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final TokenService tokenService;
    private final RedisService redisService;

    public void logout(String accessToken) {
        Claims claims = tokenService.parseToken(accessToken);

        String memberId = claims.getSubject();
        invalidateRefreshToken(memberId);
        redisService.saveBlacklistedAccessToken(accessToken, "logout");
    }

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
        redisService.saveRefreshToken(memberId, newRefreshToken);

        return new RefreshTokenDto(newRefreshToken);
    }

    public void invalidateRefreshToken(String memberId) {
        redisService.deleteToken(memberId);
    }
}
