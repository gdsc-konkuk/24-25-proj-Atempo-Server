package juton113.Atempo.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import juton113.Atempo.domain.enums.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException customException) {
        ErrorCode errorCode = customException.getErrorCode();
        logger.warn("[CustomException] code: {}, message: {}", errorCode.name(), errorCode.getMessage(), customException);

        return buildErrorResponse(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleEnumPathParamMismatch(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
        logger.warn("[EnumTypeMismatch] {}", methodArgumentTypeMismatchException.getMessage());

        return invalidEnumResponse();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJson(HttpMessageNotReadableException httpMessageNotReadableException) {
        logger.warn("[Invalid JSON Mapping] {}", httpMessageNotReadableException.getMessage());

        if (httpMessageNotReadableException.getCause() instanceof InvalidFormatException) {
            return invalidEnumResponse();
        }
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "요청 값을 확인해주세요.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnhandledException(Exception exception) {
        logger.error("[Unhandled Exception]", exception);

        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", "서버 내부 오류가 발생했습니다.");
    }

    private ResponseEntity<ErrorResponse> invalidEnumResponse() {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "INVALID_ENUM", "유효하지 않은 값이 입력되었습니다.");
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String code, String message) {
        return ResponseEntity.status(status)
                .body(new ErrorResponse(code, message));
    }
}
