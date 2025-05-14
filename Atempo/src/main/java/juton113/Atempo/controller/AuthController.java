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

@Tag(name = "Authentication, Authorization", description = "APIs related to login and token")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final TokenService tokenService;

    @Operation(summary = "Login",
            description = "Returns an endpoint for OAuth login.")
    @GetMapping("/login")
    public ResponseEntity<LoginResponseDto> login() {
        String loginUrl = "/oauth2/authorization/google";

        return ResponseEntity.ok(new LoginResponseDto("Returns OAuth login endpoint.", loginUrl));
    }

    @Operation(security = @SecurityRequirement(name = "JWT Auth"),
            summary = "Logout",
            description = "Invalidates the user's AccessToken upon logout and deletes the associated RefreshToken."
    )
    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(@Parameter(hidden = true) @RequestHeader("Authorization") String authorization) {
        String accessToken = resolveToken(authorization);
        authService.logout(accessToken);

        return ResponseEntity.noContent().build();
    }

    @Operation(security = @SecurityRequirement(name = "JWT Auth"),
            summary = "Issues AccessToken",
            description = "Issues a new AccessToken when a RefreshToken is submitted in the Authorization header."
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
            summary = "Issues RefreshToken",
            description = "Issues a new RefreshToken when a RefreshToken is submitted in the Authorization header."
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
            summary = "Handles RefreshToken blacklisting",
            description = "Restricts the issuance of a new AccessToken for a specific user. – [Admin Only]"
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
