package shop.shopBE.domain.productimage.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import shop.shopBE.global.exception.code.ExceptionCode;

@Getter
@AllArgsConstructor
public enum ProductImageExceptionCode implements ExceptionCode {
    MAIN_IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "메인 이미지를 찾을수 없습니다."),
    SIDE_IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "사이드 이미지를 찾을수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
