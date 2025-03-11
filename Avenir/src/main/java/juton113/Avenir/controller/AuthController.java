package juton113.Avenir.controller;

import juton113.Avenir.domain.dto.LoginResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @GetMapping("/login")
    public ResponseEntity<LoginResponseDto> login() {
        String loginUrl = "/oauth2/authorization/google";
        return ResponseEntity.ok(new LoginResponseDto("OAuth 로그인 URL 반환", loginUrl));
    }
}
