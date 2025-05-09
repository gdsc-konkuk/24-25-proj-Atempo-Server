package juton113.Atempo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "로그인",
            description = "Oauth 로그인을 위한 EndPoint를 반환합니다.")
    @GetMapping("/login")
    public ResponseEntity<LoginResponseDto> login() {
        String loginUrl = "/oauth2/authorization/google";

        return ResponseEntity.ok(new LoginResponseDto("OAuth 로그인 EndPoint 반환", loginUrl));
    }

    @Operation(security = @SecurityRequirement(name = "JWT Auth"),
            summary = "로그아웃",
            description = "Header의 Authorization에 AccessToken을 담아 제출하면, 로그아웃한 사용자의 AccessToken을 무효화하고 RefreshToken을 삭제합니다."
    )
    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(@Parameter(hidden = true) @RequestHeader("Authorization") String authorization) {
        String accessToken = resolveToken(authorization);
        authService.logout(accessToken);

        return ResponseEntity.noContent().build();
    }

    @Operation(security = @SecurityRequirement(name = "JWT Auth"),
            summary = "AccessToken 발급",
            description = "Header의 Authorization에 RefreshToken을 담아 제출하면, 신규 AccessToken을 발급합니다."
    )
    @PostMapping("/access-token")
    public ResponseEntity<?> reissueAccessToken(@Parameter(hidden = true) @RequestHeader("Authorization") String authorization) {
        String refreshToken = resolveToken(authorization);
        AccessTokenDto accessTokenDto = authService.reissueAccessToken(refreshToken);

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + accessTokenDto.getAccessToken())
                .body("AccessToken Reissued");
    }

    @Operation(security = @SecurityRequirement(name = "JWT Auth"),
            summary = "RefreshToken 토큰 발급",
            description = "Header의 Authorization에 RefreshToken을 제출하면, 신규 RefreshToken을 발급합니다."
    )
    @PostMapping("/refresh-token")
    public ResponseEntity<?> reissueRefreshToken(@Parameter(hidden = true) @RequestHeader("Authorization") String authorization) {
        String refreshToken = resolveToken(authorization);
        RefreshTokenDto refreshTokenDto = authService.reissueRefreshToken(refreshToken);

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + refreshTokenDto.getRefreshToken())
                .body("RefreshToken Reissued");
    }

    @Operation(security = @SecurityRequirement(name = "JWT Auth"),
            summary = "RefreshToken BlackList 처리",
            description = "특정 사용자의 신규 AccessToken 발급을 제한합니다. - [관리자] 기능"
    )
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
