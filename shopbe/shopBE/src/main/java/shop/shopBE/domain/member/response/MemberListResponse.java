package shop.shopBE.domain.member.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import shop.shopBE.domain.member.entity.enums.Gender;
import shop.shopBE.domain.member.entity.enums.Role;

import java.time.LocalDateTime;

public record MemberListResponse(
        String name, // 회원의 이름
        String email, // 회원의 이메일
        String tel, // 회원의 전화번호
        Gender gender, // 회원의 성별
        Role role // 회원의 역할
) {
}
