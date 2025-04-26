package juton113.Atempo.exception;

import juton113.Atempo.domain.enums.ErrorCode;
import lombok.Getter;

public class CustomException extends RuntimeException {
    @Getter
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage()); // logging setting
        this.errorCode = errorCode;
    }
}
