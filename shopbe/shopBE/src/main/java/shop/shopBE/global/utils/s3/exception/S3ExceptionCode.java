package shop.shopBE.global.utils.s3.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import shop.shopBE.global.exception.code.ExceptionCode;

@AllArgsConstructor
@Getter
public enum S3ExceptionCode implements ExceptionCode {

    AWS_SERVICE_EXCEPTION(HttpStatus.SERVICE_UNAVAILABLE, "AWS 서비스 오류"),
    AWS_CLIENT_EXCEPTION(HttpStatus.SERVICE_UNAVAILABLE, "AWS 클라이언트 오류"),
    AWS_IOE(HttpStatus.SERVICE_UNAVAILABLE, "파일 업로드 실패"),;


    private final HttpStatus httpStatus;
    private final String message;

}
