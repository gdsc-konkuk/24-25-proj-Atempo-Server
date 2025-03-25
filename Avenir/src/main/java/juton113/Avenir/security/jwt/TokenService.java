package juton113.Avenir.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import juton113.Avenir.domain.enums.TokenType;
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

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        return  (bearerToken != null && bearerToken.startsWith("Bearer ")) ? bearerToken.substring(7) : null;
    }
}

