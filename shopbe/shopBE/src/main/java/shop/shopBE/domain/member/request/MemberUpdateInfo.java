package shop.shopBE.domain.member.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import shop.shopBE.domain.member.entity.enums.Gender;
import shop.shopBE.domain.member.entity.enums.Role;

@Builder
public record MemberUpdateInfo(
        @NotNull Gender gender,
        @NotNull String tel,
        @NotNull Role role
) {
    public static MemberUpdateInfo createDefaultMemberUpdateInfo(Gender gender, String tel, Role role) {
        return MemberUpdateInfo.builder()
                .gender(gender)
                .tel(tel)
                .role(role)
                .build();
    }
}
