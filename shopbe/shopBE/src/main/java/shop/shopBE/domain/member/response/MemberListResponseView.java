package shop.shopBE.domain.member.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MemberListResponseView(
        String name, // 회원의 이름
        String email, // 회원의 이메일
        String gender, // 회원의 성별
        String tel,// 회원의 전화번호
        String role // 회원의 역할
        ) {
}
