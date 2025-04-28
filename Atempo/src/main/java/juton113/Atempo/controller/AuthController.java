package juton113.Atempo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import juton113.Atempo.domain.dto.AccessTokenDto;
import juton113.Atempo.domain.dto.LoginResponseDto;
import juton113.Atempo.domain.dto.RefreshTokenDto;
import juton113.Atempo.domain.enums.ErrorCode;
import juton113.Atempo.exception.CustomException;
import juton113.Atempo.security.jwt.TokenService;
import juton113.Atempo.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "인증, 인가", description = "로그인, 토큰 발급과 관련된 API")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
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
    public ResponseEntity<?> reissueAccessToken(@RequestHeader("Authorization") String authorization) {
        String refreshToken = resolveToken(authorization);
        AccessTokenDto accessTokenDto = authService.reissueAccessToken(refreshToken);

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + accessTokenDto.getAccessToken())
                .body("Access Token reissued");
    }

    @Operation(security = @SecurityRequirement(name = "JWT Auth"),
            summary = "Refresh 토큰 발급",
            description = "Refresh 토큰을 Authorization: Bearer edy7esvas... 형태로 제출하여 Refresh Token을 발급 받는다."
    )
    @PostMapping("/refresh-token")
    public ResponseEntity<?> reissueRefreshToken(@RequestHeader("Authorization") String authorization) {
        String refreshToken = resolveToken(authorization);
        RefreshTokenDto refreshTokenDto = authService.reissueRefreshToken(refreshToken);

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + refreshTokenDto.getRefreshToken())
                .body("Refresh Token reissued");
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/refresh-token/{memberId}")
    private ResponseEntity<?> invalidateRefreshToken(@PathVariable("memberId") String memberId) {
        authService.invalidateRefreshToken(memberId);

        return ResponseEntity.noContent().build();
    }

    private String resolveToken(String bearerToken) {
        if (bearerToken == null) {
            throw new CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
        } else if (!bearerToken.startsWith("Bearer ")) {
            throw new CustomException(ErrorCode.INVALID_AUTH_HEADER_FORMAT);
        }

        return bearerToken.substring(7);
    }

}
