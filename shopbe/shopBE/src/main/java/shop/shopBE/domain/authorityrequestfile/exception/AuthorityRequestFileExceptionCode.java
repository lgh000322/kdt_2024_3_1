package shop.shopBE.domain.authorityrequestfile.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import shop.shopBE.global.exception.code.ExceptionCode;

@AllArgsConstructor
@Getter
public enum AuthorityRequestFileExceptionCode implements ExceptionCode {
    AUTHORITY_REQUEST_FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 파일들을 찾을 수 없습니다."),;

    private final HttpStatus httpStatus;
    private final String message;
}
