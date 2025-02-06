package shop.shopBE.global.refresh.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import shop.shopBE.global.exception.code.ExceptionCode;

@AllArgsConstructor
@Getter
public enum RefreshExceptionCode implements ExceptionCode {
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "refreshToken을 찾을 수 없습니다."),;

    private final HttpStatus httpStatus;
    private final String message;

}
