package shop.shopBE.domain.product.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import shop.shopBE.global.exception.code.ExceptionCode;

@AllArgsConstructor
@Getter
public enum ProductExceptionCode implements ExceptionCode {
    PRODUCT_EMPTY(HttpStatus.NOT_FOUND, "등록된 상품이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."),
    NOT_FOUND_PRODUCT_BY_SELLER(HttpStatus.NOT_FOUND, "판매자가 등록한 상품을 찾을수 없습니다."),
    INVALID_MINUS_STOCK_REQUEST(HttpStatus.BAD_REQUEST, "재고가 0개입니다. 더 이상 재고를 차감할 수 없습니다."),
    INVALID_MINUS_LIKE_REQUEST(HttpStatus.BAD_REQUEST, "좋아요가 0개입니다. 더 이상 좋아요를 감소할 수 없습니다."),
    INVALID_OPTION(HttpStatus.BAD_REQUEST, "잘못된 분류 설정입니다."),
    INVALID_PRODUCT_CATEGORY(HttpStatus.BAD_REQUEST, "잘못된 상품 카테고리 입니다."),
    OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "재고가 부족합니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
