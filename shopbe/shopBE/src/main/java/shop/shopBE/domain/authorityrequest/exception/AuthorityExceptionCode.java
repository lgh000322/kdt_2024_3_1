package shop.shopBE.domain.authorityrequest.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import shop.shopBE.global.exception.code.ExceptionCode;

@AllArgsConstructor
@Getter
public enum AuthorityExceptionCode implements ExceptionCode {

    AUTHORITY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 판매자 권한 신청 요청을 찾을 수 없습니다."),
    AUTHORITY_NOT_EXISTS(HttpStatus.NO_CONTENT, "판매자 권한 요청 목록이 비어있습니다."),;
    private final HttpStatus httpStatus;
    private final String message;

}
