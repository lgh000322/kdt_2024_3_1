package shop.shopBE.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import shop.shopBE.global.exception.code.ExceptionCode;
import shop.shopBE.global.exception.custom.CustomException;
import shop.shopBE.global.response.ResponseFormat;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class ExceptionProcessorFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper = new ObjectMapper(); // ObjectMapper 인스턴스

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response); // 다음 필터로 전달
        } catch (CustomException e) {
            // 예외 발생 시 처리
            sendException(response, e);
        }
    }

    private void sendException(HttpServletResponse response, CustomException ex) throws IOException {
        // 응답 상태 코드 설정
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        // 응답 Content-Type을 JSON으로 설정
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // 캐릭터 인코딩 설정
        response.setCharacterEncoding("UTF-8");

        ExceptionCode exceptionCode = ex.getExceptionCode();
        int httpStatusValue = exceptionCode.getHttpStatus().value();
        String message = exceptionCode.getMessage();

        // Map을 JSON으로 변환
        String jsonResponse = objectMapper.writeValueAsString(ResponseFormat.fail(httpStatusValue, message));

        // 응답 본문에 JSON 데이터 출력
        PrintWriter writer = response.getWriter();
        writer.write(jsonResponse);
        writer.flush();
    }
}
