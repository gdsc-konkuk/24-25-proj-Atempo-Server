package juton113.Avenir.controller;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import juton113.Avenir.domain.dto.LoginResponseDto;
import juton113.Avenir.domain.dto.TokenResponseDto;
import juton113.Avenir.domain.enums.TokenType;
import juton113.Avenir.security.jwt.TokenService;
import juton113.Avenir.service.AuthService;
import juton113.Avenir.service.RedisService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final RedisService redisService;
    private final TokenService tokenService;

    @GetMapping("/login")
    public ResponseEntity<LoginResponseDto> login() {
        String loginUrl = "/oauth2/authorization/google";

        return ResponseEntity.ok(new LoginResponseDto("OAuth 로그인 URL 반환", loginUrl));
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissueToken(HttpServletRequest request) {
        String refreshToken = tokenService.resolveToken(request, TokenType.REFRESH);

        if (refreshToken == null) {
            // TODO: tokenService.resolveToken 내에 예외 처리 로직 작성 후 지울 것
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid Token");
        }

        Claims claims = tokenService.parseToken(refreshToken);
        String memberId = claims.getSubject();

        Optional<String> optionalToken = redisService.getToken(memberId);
        if(optionalToken.isEmpty() || !optionalToken.get().equals(refreshToken)) {
            // TODO: redisService.getToken 내에 예외 처리 로직 작성 후 지울 것
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid Token");
        }

        TokenResponseDto tokenResponseDto = authService.issueToken(memberId);
        String newRefreshToken = tokenResponseDto.getRefreshToken();

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + tokenResponseDto.getAccessToken())
                .header("X-Refresh-Token", "Bearer " + newRefreshToken)
                .body("Token reissued");
    }

}
