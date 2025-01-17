package shop.shopBE.global.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.shopBE.global.exception.custom.CustomException;
import shop.shopBE.global.exception.errorcode.ErrorCode;
import shop.shopBE.global.response.ResponseFormat;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 커스텀 예외 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseFormat<?>> handleCustomException(CustomException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        log.info("ErrorMessage={}", errorCode.getMessage());
        ResponseFormat<?> response = ResponseFormat.fail(errorCode.getHttpStatus().value(), errorCode.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
    }

    //Valid 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseFormat<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ResponseFormat<Map<String, String>> response = ResponseFormat.fail(
                400,
                "유효성 검사에 실패했습니다.",
                errors
        );

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }
}
