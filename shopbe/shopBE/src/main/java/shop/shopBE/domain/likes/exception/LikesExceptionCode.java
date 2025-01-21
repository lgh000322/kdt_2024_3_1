package shop.shopBE.domain.likes.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import shop.shopBE.global.exception.code.ExceptionCode;

@AllArgsConstructor
@Getter
public enum LikesExceptionCode implements ExceptionCode {

    LIKES_NOT_FOUND(HttpStatus.NOT_FOUND, "회원의 찜 보관함을 찾을 수 없습니다."),;

    private final HttpStatus httpStatus;
    private final String message;

}
