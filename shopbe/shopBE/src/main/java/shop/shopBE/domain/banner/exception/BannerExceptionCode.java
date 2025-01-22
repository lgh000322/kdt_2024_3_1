package shop.shopBE.domain.banner.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import shop.shopBE.global.exception.code.ExceptionCode;

@AllArgsConstructor
@Getter
public enum BannerExceptionCode implements ExceptionCode {
    BANNER_EMPTY(HttpStatus.NO_CONTENT, "배너가 없습니다."),
    BANNER_NOT_FOUND(HttpStatus.NOT_FOUND, "배너를 찾을 수 없습니다."),;

    private final HttpStatus httpStatus;
    private final String message;


}
