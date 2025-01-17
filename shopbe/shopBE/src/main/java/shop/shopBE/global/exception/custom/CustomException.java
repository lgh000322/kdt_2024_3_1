package shop.shopBE.global.exception.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.shopBE.global.exception.errorcode.ErrorCode;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {
    private ErrorCode errorCode;
}
