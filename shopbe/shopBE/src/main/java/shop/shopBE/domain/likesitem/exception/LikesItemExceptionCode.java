package shop.shopBE.domain.likesitem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import shop.shopBE.global.exception.code.ExceptionCode;

@AllArgsConstructor
@Getter
public enum LikesItemExceptionCode implements ExceptionCode {

    LIKES_ITEM_EMPTY(HttpStatus.NO_CONTENT, "찜 아이템이 비어있습니다."),;

    private final HttpStatus httpStatus;
    private final String message;
}
