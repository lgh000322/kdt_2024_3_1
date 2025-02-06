package shop.shopBE.global.filter.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import shop.shopBE.global.exception.code.ExceptionCode;

@AllArgsConstructor
@Getter
public enum JWTExceptionCode implements ExceptionCode {
    JWT_INVALID_MALFORMED(HttpStatus.FORBIDDEN, "유효하지 않은 JWT입니다(MALFORMED)."),
    JWT_EXPIRED(HttpStatus.FORBIDDEN, "만료된 JWT입니다."),
    JWT_UNSUPPORTED(HttpStatus.FORBIDDEN, "지원하지 않는 JWT입니다."),
    JWT_CLAIM_EMPTY(HttpStatus.NO_CONTENT, "claim에 내용이 없습니다."),
    JWT_INVALID(HttpStatus.FORBIDDEN, "JWT 유효성 검사에 실패했습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

}
