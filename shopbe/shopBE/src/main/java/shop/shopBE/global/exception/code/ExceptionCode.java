package shop.shopBE.global.exception.code;

import org.springframework.http.HttpStatus;

public interface ExceptionCode {
    HttpStatus getHttpStatus();
    String getMessage();
}
