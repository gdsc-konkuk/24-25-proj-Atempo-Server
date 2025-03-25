package juton113.Avenir.controller;

import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import juton113.Avenir.domain.dto.AccessTokenDto;
import juton113.Avenir.domain.dto.LoginResponseDto;
import juton113.Avenir.domain.dto.RefreshTokenDto;
import juton113.Avenir.domain.enums.TokenType;
import juton113.Avenir.security.jwt.TokenService;
import juton113.Avenir.service.AuthService;
import juton113.Avenir.service.RedisService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static juton113.Avenir.domain.enums.TokenType.REFRESH;

@Tag(name = "인증, 인가", description = "로그인, 토큰 발급과 관련된 API")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final RedisService redisService;
    private final TokenService tokenService;

    @Operation(summary = "로그인 요청",
            description = "Oauth 로그인 url 요청")
    @GetMapping("/login")
    public ResponseEntity<LoginResponseDto> login() {
        String loginUrl = "/oauth2/authorization/google";

        return ResponseEntity.ok(new LoginResponseDto("OAuth 로그인 URL 반환", loginUrl));
    }

    @Operation(security = @SecurityRequirement(name = "JWT Auth"),
            summary = "Access 토큰 발급",
            description = "Refresh 토큰을 Authorization: Bearer edy7esvas... 형태로 제출하여 Access Token을 발급 받는다."
    )
    @PostMapping("/access-token")
    public ResponseEntity<?> reissueAccessToken(HttpServletRequest request) {
        String refreshToken = tokenService.resolveToken(request);

        if (refreshToken == null) {
            // TODO: tokenService.resolveToken 내에 예외 처리 로직 작성 후 지울 것
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid Token - Empty");
        }

        Claims claims = tokenService.parseToken(refreshToken);

        if (!claims.get("tokenType").equals(TokenType.REFRESH.toString())) {
            // TODO: tokenService.resolveToken 내에 예외 처리 로직 작성 후 지울 것
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid Token - Different");
        }

        String memberId = claims.getSubject();

        Optional<String> optionalToken = redisService.getToken(memberId);
        if(optionalToken.isEmpty() || !optionalToken.get().equals(refreshToken)) {
            // TODO: redisService.getToken 내에 예외 처리 로직 작성 후 지울 것
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid Token");
        }

        AccessTokenDto accessTokenDto = authService.issueAccessToken(memberId);

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + accessTokenDto.getAccessToken())
                .body("Access Token reissued");
    }

    @Operation(security = @SecurityRequirement(name = "JWT Auth"),
            summary = "Refresh 토큰 발급",
            description = "Refresh 토큰을 Authorization: Bearer edy7esvas... 형태로 제출하여 Refresh Token을 발급 받는다."
    )
    @PostMapping("/refresh-token")
    public ResponseEntity<?> reissueRefreshToken(HttpServletRequest request) {
        String refreshToken = tokenService.resolveToken(request);

        if (refreshToken == null) {
            // TODO: tokenService.resolveToken 내에 예외 처리 로직 작성 후 지울 것
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid Token - Empty");
        }

        Claims claims = tokenService.parseToken(refreshToken);

        if (!claims.get("tokenType").equals(TokenType.REFRESH.toString())) {
            // TODO: tokenService.resolveToken 내에 예외 처리 로직 작성 후 지울 것
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid Token - Different");
        }

        String memberId = claims.getSubject();

        Optional<String> optionalToken = redisService.getToken(memberId);
        if(optionalToken.isEmpty() || !optionalToken.get().equals(refreshToken)) {
            // TODO: redisService.getToken 내에 예외 처리 로직 작성 후 지울 것
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid Token");
        }

        RefreshTokenDto refreshTokenDto = authService.issueRefreshToken(memberId);

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + refreshTokenDto.getRefreshToken())
                .body("Refresh Token reissued");
    }

}
