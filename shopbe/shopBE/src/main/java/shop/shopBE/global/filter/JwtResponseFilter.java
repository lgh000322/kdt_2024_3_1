package shop.shopBE.global.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import shop.shopBE.global.exception.custom.CustomException;
import shop.shopBE.global.response.ResponseFormat;

import java.io.IOException;

@Component
public class JwtResponseFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (CustomException e) {
            sendErrorResponse(response, e);
        }
    }

    private void sendErrorResponse(HttpServletResponse response, CustomException ex) throws IOException {
        response.setStatus(ex.getExceptionCode().getHttpStatus().value());
        response.setContentType("application/json; charset=UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();

        String jsonStr = objectMapper.writeValueAsString(ResponseFormat.fail(ex.getExceptionCode().getHttpStatus().value(), ex.getExceptionCode().getMessage()));

        response.getWriter().write(jsonStr);
    }
}
