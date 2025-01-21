package shop.shopBE.domain.member.request;

import jakarta.validation.constraints.NotNull;
import shop.shopBE.domain.member.entity.enums.Gender;
import shop.shopBE.domain.member.entity.enums.Role;

public record MemberUpdateInfo(
        @NotNull Gender gender,
        @NotNull String tel,
        @NotNull Role role
) {
}
