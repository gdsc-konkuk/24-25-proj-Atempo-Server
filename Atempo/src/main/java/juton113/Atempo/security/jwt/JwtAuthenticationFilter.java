package juton113.Atempo.security.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import juton113.Atempo.domain.enums.ErrorCode;
import juton113.Atempo.domain.enums.TokenType;
import juton113.Atempo.exception.JwtAuthenticationException;
import juton113.Atempo.service.RedisService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final RedisService redisService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);

        if (token == null) {
            request.setAttribute("errorCode", ErrorCode.TOKEN_EXPIRED);
            throw new JwtAuthenticationException(ErrorCode.AUTH_HEADER_MISSING);
        }

        if (redisService.validateBlacklistedToken(token)) {
            request.setAttribute("errorCode", ErrorCode.LOGOUT_TOKEN);
            throw new JwtAuthenticationException(ErrorCode.LOGOUT_TOKEN);
        }

        try {
            Claims claims = tokenService.parseToken(token);

            if (!claims.get("tokenType").equals(TokenType.ACCESS.toString())) {
                request.setAttribute("errorCode", ErrorCode.INVALID_TOKEN_TYPE);
                throw new JwtAuthenticationException(ErrorCode.INVALID_TOKEN_TYPE);
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (JwtAuthenticationException jwtException) {
            request.setAttribute("errorCode", jwtException.getErrorCode());
            throw jwtException;
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken == null) return null;
        if (!bearerToken.startsWith("Bearer ")) {
            request.setAttribute("errorCode", ErrorCode.INVALID_AUTH_HEADER_FORMAT);
            throw new JwtAuthenticationException(ErrorCode.INVALID_AUTH_HEADER_FORMAT);
        }

        return bearerToken.substring(7);
    }
}
