package shop.shopBE.domain.cartitem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import shop.shopBE.global.exception.code.ExceptionCode;

@Getter
@AllArgsConstructor
public enum CartItemExceptionCode implements ExceptionCode {
    CART_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "장바구니에 상품이 없습니다.");

    private final HttpStatus httpStatus;

    private final String message;
}
