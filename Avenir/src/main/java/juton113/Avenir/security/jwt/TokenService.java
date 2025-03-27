package juton113.Avenir.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import juton113.Avenir.domain.enums.ErrorCode;
import juton113.Avenir.domain.enums.TokenType;
import juton113.Avenir.exception.CustomException;
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
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public void validateTokenType(Claims claims, TokenType expectedTokenType) {
        if (!claims.get("tokenType").equals(expectedTokenType.toString()))
            throw new CustomException(ErrorCode.INVALID_TOKEN_TYPE);
    }
}

