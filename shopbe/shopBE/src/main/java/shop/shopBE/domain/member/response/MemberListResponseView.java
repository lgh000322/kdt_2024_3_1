package shop.shopBE.domain.member.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record MemberListResponseView(
        String name, // 회원의 이름
        String email, // 회원의 이메일
        @JsonFormat(shape =JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd")
        LocalDate createdAt,
        String role
        ) {
}
