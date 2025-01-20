package shop.shopBE.global.config.security.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import shop.shopBE.global.exception.code.ExceptionCode;

@Getter
@AllArgsConstructor
public enum OauthExceptionCode implements ExceptionCode {
    NOT_SUPPORTED(HttpStatus.BAD_REQUEST, "지원되지 않는 로그인 방식입니다.");


    private final HttpStatus httpStatus;
    private final String message;
    }
