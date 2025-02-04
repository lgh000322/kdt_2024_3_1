package shop.shopBE.domain.member.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import shop.shopBE.domain.member.entity.enums.Gender;
import shop.shopBE.domain.member.entity.enums.Role;

@Builder
public record MemberUpdateInfo(
        @NotNull String email,
        @NotNull String name,
        @NotNull Gender gender,
        @NotNull String phone
) {

}
