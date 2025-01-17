package shop.shopBE.global.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public record ResponseFormat<T>(
        int code,
        String message,
        T data,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss") String timestamp
) {

    // 성공하고 data에 값을 추가해야 할 때 사용.
    public static <T> ResponseFormat<T> of(String message, T data) {
        return new ResponseFormat<>(200, message, data, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    // 성공하고 data에 값을 넣어주지 않아도 될 때 사용.
    public static <T> ResponseFormat<T> of(String message) {
        return new ResponseFormat<>(200, message, null, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    // 실패하고 data에 값을 넣어주지 않아도 될 때 사용.
    public static <T> ResponseFormat<T> fail(Integer code, String message) {
        return new ResponseFormat<>(code, message, null, LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
    }

    // 실패하고 data에 값을 넣어줘야 할 때 사용.
    public static <T> ResponseFormat<T> fail(Integer code, String message, T data) {
        return new ResponseFormat<>(code, message, data, LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
    }
}
