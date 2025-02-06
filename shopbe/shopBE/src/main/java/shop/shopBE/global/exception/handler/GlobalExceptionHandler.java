package shop.shopBE.global.exception.handler;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import shop.shopBE.global.exception.custom.CustomException;
import shop.shopBE.global.exception.code.ExceptionCode;
import shop.shopBE.global.response.ResponseFormat;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 커스텀 예외 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseFormat<?>> handleCustomException(CustomException ex) {
        ExceptionCode exceptionCode = ex.getExceptionCode();
        log.info("ErrorMessage={}", exceptionCode.getMessage());
        ResponseFormat<?> response = ResponseFormat.fail(exceptionCode.getHttpStatus().value(), exceptionCode.getMessage());
        return ResponseEntity.status(exceptionCode.getHttpStatus()).body(response);
    }

    //Valid 예외 처리 - 유효성 검사 실패
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

    // 필드 값이 잘못된 경우 발생하는 예외
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    //RequestParam의 값과 일치하지 않을경우 badrequest보내줌.
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseFormat<Void>> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        // 파라미터 이름, 전달된 값, 예외가 발생한 파라미터 타입을 가져옴
        String paramName = ex.getName(); // 파라미터 이름
        String paramValue = String.valueOf(ex.getValue()); // 잘못된 값

        // 에러 메시지 생성
        String errorMessage = String.format("잘못된 값이 전달되었습니다: 파라미터 '%s'에 대한 값 '%s'은 타입에 맞지 않습니다.",
                paramName, paramValue);

        ResponseFormat<Void> response = ResponseFormat.fail(
                400,
                errorMessage
        );
        return ResponseEntity.badRequest().body(response);
    }
}
