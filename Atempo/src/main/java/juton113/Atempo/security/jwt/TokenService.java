package juton113.Atempo.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import juton113.Atempo.domain.enums.ErrorCode;
import juton113.Atempo.domain.enums.TokenType;
import juton113.Atempo.exception.JwtAuthenticationException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class TokenService {
    @Value("${jwt.secret}")
    private String secret;

    @Getter
    @Value("${jwt.access-token-expiration}")
    private long accessTokenTime;

    @Getter
    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenTime;

    private TokenType tokenType;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String createAccessToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .claim("tokenType", TokenType.ACCESS)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .claim("tokenType", TokenType.REFRESH)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtAuthenticationException(ErrorCode.TOKEN_EXPIRED);
        } catch (MalformedJwtException | SignatureException e) {
            throw new JwtAuthenticationException(ErrorCode.INVALID_SIGNATURE);
        } catch (JwtException e) {
            throw new JwtAuthenticationException(ErrorCode.MALFORMED_TOKEN);
        }
    }

    public void validateTokenType(Claims claims, TokenType expectedTokenType) {
        if (!claims.get("tokenType").equals(expectedTokenType.toString()))
            throw new JwtAuthenticationException(ErrorCode.INVALID_TOKEN_TYPE);
    }

    public long getExpiration(String token) {
        Date expiration = parseToken(token).getExpiration();
        return expiration.getTime() - System.currentTimeMillis();
    }
}

