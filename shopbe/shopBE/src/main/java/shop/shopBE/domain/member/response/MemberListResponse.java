package shop.shopBE.domain.member.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import shop.shopBE.domain.member.entity.enums.Role;

import java.time.LocalDateTime;

public record MemberListResponse(
        String name, // 회원의 이름
        String email, // 회원의 이메일
        LocalDateTime createdAt, // 회원 생성 날짜
        Role role // 회원의 등급
) {
}
