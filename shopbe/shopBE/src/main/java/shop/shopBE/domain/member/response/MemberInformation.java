package shop.shopBE.domain.member.response;

import shop.shopBE.domain.member.entity.enums.Gender;

public record MemberInformation(
        String name,
        String email,
        String tel,
        Gender gender
) {
}
