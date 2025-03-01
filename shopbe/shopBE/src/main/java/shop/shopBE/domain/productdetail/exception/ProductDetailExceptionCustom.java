package shop.shopBE.domain.productdetail.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import shop.shopBE.global.exception.code.ExceptionCode;
import shop.shopBE.global.exception.custom.CustomException;

@Getter
@AllArgsConstructor
public enum ProductDetailExceptionCustom implements ExceptionCode {
    PRODUCT_DETAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 상품 정보를 찾을수 없습니다."),
    OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "재고가 부족합니다."),
    STOCK_NOT_FOUNT(HttpStatus.NOT_FOUND, "재고를 확인할 수 없습니다.");

    private final HttpStatus httpStatus;

    private final String message;

}
