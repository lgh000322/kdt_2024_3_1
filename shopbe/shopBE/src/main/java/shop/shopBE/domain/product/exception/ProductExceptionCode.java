package shop.shopBE.domain.product.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import shop.shopBE.global.exception.code.ExceptionCode;

@AllArgsConstructor
@Getter
public enum ProductExceptionCode implements ExceptionCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."),;

    private final HttpStatus httpStatus;
    private final String message;

}
