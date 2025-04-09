package juton113.Avenir.exception;

import juton113.Avenir.domain.enums.ErrorCode;
import lombok.Getter;

public class CustomException extends RuntimeException {
    @Getter
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage()); // logging setting
        this.errorCode = errorCode;
    }
}
