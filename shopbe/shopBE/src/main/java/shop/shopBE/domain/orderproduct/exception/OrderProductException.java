package shop.shopBE.domain.orderproduct.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import shop.shopBE.global.exception.code.ExceptionCode;


@AllArgsConstructor
@Getter
public enum OrderProductException implements ExceptionCode {

    OrderProduct_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 주문상세를 찾을 수 없습니다."),;

    private final HttpStatus httpStatus;
    private final String message;

}
