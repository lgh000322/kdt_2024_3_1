package shop.shopBE.global.filter.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import shop.shopBE.global.exception.code.ExceptionCode;

@AllArgsConstructor
@Getter
public enum JwtExceptionCode implements ExceptionCode {
    JWT_NOT_FOUND(HttpStatus.NOT_FOUND, "JWT를 찾을 수 없습니다."),
    JWT_NOT_VALID(HttpStatus.UNAUTHORIZED,"유효하지 않은 JWT 입니다."),
    JWT_EXPIRED(HttpStatus.FORBIDDEN, "JWT의 유효기간이 지났습니다."),
    JWT_NOT_SUPPORTED(HttpStatus.UNAUTHORIZED, "지윈되지 않는 JWT 형식입니다."),
    JWT_WRONG_FORM(HttpStatus.BAD_REQUEST, "잘못된 JWT 형식입니다."),

    ;
    private final HttpStatus httpStatus;
    private final String message;
}
