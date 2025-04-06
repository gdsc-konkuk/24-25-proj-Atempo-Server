package juton113.Avenir.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Member 관련 예외
    USER_NOT_FOUND("사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // Token 관련 예외
    REFRESH_TOKEN_NOT_FOUND("Refresh 토큰이 존재하지 않습니다", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN_TYPE("잘못된 토큰 종류입니다. Refresh 토큰이 필요합니다.", HttpStatus.UNAUTHORIZED),

    // Header 관련 예외
    AUTH_HEADER_MISSING("Authorization 헤더가 존재하지 않습니다", HttpStatus.UNAUTHORIZED),
    INVALID_AUTH_HEADER_FORMAT("Authorization 헤더의 형식이 잘못되었습니다", HttpStatus.UNAUTHORIZED),

    // HospitalCallStatus
    HOSPITAL_CALL_STATUS_NOT_FOUND("입원 전화 상태를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus httpStatus;
}
